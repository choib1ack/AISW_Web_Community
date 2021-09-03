package com.aisw.community.service.post.comment;

import com.aisw.community.component.advice.exception.CommentNotFoundException;
import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.comment.CommentApiRequest;
import com.aisw.community.model.network.request.user.AlertApiRequest;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.comment.CustomCommentRepository;
import com.aisw.community.service.user.AlertService;
import com.aisw.community.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomCommentRepository customCommentRepository;

    @Autowired
    private AlertService alertService;

    @Transactional
    @CacheEvict(value = "commentSearchByPost", key = "#boardId")
    public Header<CommentApiResponse> create(User user, Long boardId, CommentApiRequest commentApiRequest) {
        Board board = boardRepository.findByIdWithComment(commentApiRequest.getBoardId()).orElseThrow(
                () -> new PostNotFoundException(commentApiRequest.getBoardId()));

        Comment superComment = commentApiRequest.getSuperCommentId() != null ?
                getRootComment(commentApiRequest.getSuperCommentId()) : null;
        Comment comment = Comment.builder()
                .content(commentApiRequest.getContent())
                .isAnonymous(commentApiRequest.getIsAnonymous())
                .isDeleted(false)
                .board(board)
                .user(user)
                .superComment(superComment)
                .board(board)
                .build();

        // 익명 선택 시 익명 고유 번호 부여
        if(!commentApiRequest.getIsAnonymous()) comment.setWriter(user.getName());
        else {
            if(board.getUser().getId() == user.getId()) comment.setWriter("글쓴이");
            else {
                List<Comment> commentList = board.getCommentList();
                long cnt = 1;
                for(Comment c : commentList) {
                    if(c.getWriter().startsWith("익명")) {
                        if (c.getUser().getId() == user.getId()) {
                            comment.setWriter(c.getWriter());
                            break;
                        } else cnt = Math.max(cnt,
                                Long.parseLong(c.getWriter().replace("익명", "")) + 1);
                    }
                }
                comment.setWriter("익명" + cnt);
            }
        }
        Comment newComment = commentRepository.save(comment);

        AlertApiRequest alertApiRequest = AlertApiRequest.builder()
                .commentId(newComment.getId())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .postId(board.getId())
                .build();
        if(comment.getContent().length() < 20) {
            alertApiRequest.setContent(comment.getContent());
        } else {
            alertApiRequest.setContent(comment.getContent().substring(0, 20));
        }
        if(user.getId() != board.getUser().getId()) {
            alertApiRequest.setUserId(board.getUser().getId());
            alertService.create(alertApiRequest);
        }
        if(superComment != null && user.getId() != superComment.getUser().getId()) {
            alertApiRequest.setUserId(superComment.getUser().getId());
            alertService.create(alertApiRequest);
        }

        return Header.OK(response(newComment));
    }

    @Transactional
    @CacheEvict(value = "commentSearchByPost", key = "#boardId")
    public Header delete(User user, Long boardId, Long commentId) {
        Comment comment = commentRepository.findCommentByIdWithSuperComment(commentId).orElseThrow(
                () -> new CommentNotFoundException(commentId));

        if (comment.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        if(comment.getSubComment().size() != 0) {
            comment.setIsDeleted(true);
        } else {
            commentRepository.delete(getDeletableAncestorComment(comment));
        }

        return Header.OK();
    }

    private Comment getDeletableAncestorComment(Comment comment) {
        Comment superComment = comment.getSuperComment();
        if(superComment != null && superComment.getSubComment().size() == 1 && superComment.getIsDeleted() == true)
            return getDeletableAncestorComment(superComment);
        return comment;
    }

    private Comment getRootComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        return findRootComment(comment);
    }

    private Comment findRootComment(Comment comment) {
        Comment superComment = comment.getSuperComment();
        if(superComment == null) {
            return comment;
        } else {
            return findRootComment(superComment);
        }
    }

    private CommentApiResponse response(Comment comment) {
        CommentApiResponse freeCommentApiResponse = CommentApiResponse.builder()
                .id(comment.getId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .likes(comment.getLikes())
                .isAnonymous(comment.getIsAnonymous())
                .boardId(comment.getBoard().getId())
                .build();

        return freeCommentApiResponse;
    }

    @Cacheable(value = "commentSearchByPost", key = "#id")
    public List<CommentApiResponse> searchByPost(Long id) {
        List<Comment> comments = customCommentRepository.findCommentByBoardId(id);

        List<CommentApiResponse> commentApiResponseList = new ArrayList<>();
        Map<Long, CommentApiResponse> map = new HashMap<>();
        comments.stream().forEach(comment -> {
            CommentApiResponse commentApiResponse = CommentApiResponse.convertCommentToDto(comment);
            if(comment.getSuperComment() == null) {
                map.put(commentApiResponse.getId(), commentApiResponse);
                commentApiResponseList.add(commentApiResponse);
            } else {
                map.get(comment.getSuperComment().getId()).getSubComment().add(commentApiResponse);
            }
        });
        return commentApiResponseList;
    }

    public List<CommentApiResponse> searchByPost(User user, Long id) {
        List<Comment> comments = customCommentRepository.findCommentByBoardId(id);

        List<CommentApiResponse> commentApiResponseList = new ArrayList<>();
        Map<Long, CommentApiResponse> map = new HashMap<>();
        comments.stream().forEach(comment -> {
            CommentApiResponse commentApiResponse = CommentApiResponse.convertCommentToDto(comment);
            commentApiResponse.setIsWriter((user.getId() == comment.getUser().getId()) ? true : false);
            if(comment.getSuperComment() == null) {
                map.put(commentApiResponse.getId(), commentApiResponse);
                commentApiResponseList.add(commentApiResponse);
            } else {
                map.get(comment.getSuperComment().getId()).getSubComment().add(commentApiResponse);
            }
        });
        return commentApiResponseList;
    }
}
