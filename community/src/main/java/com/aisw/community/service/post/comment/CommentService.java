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

    @Autowired
    private UserService userService;

    @Transactional
    @CacheEvict(value = "commentSearchByPost", key = "#boardId")
    public Header<CommentApiResponse> create(User user, Long boardId, CommentApiRequest commentApiRequest) {
        Board board = boardRepository.findById(commentApiRequest.getBoardId()).orElseThrow(
                () -> new PostNotFoundException(commentApiRequest.getBoardId()));
        List<Comment> commentList = board.getCommentList();
        long cnt = 1;
        for(int i = commentList.size() - 1; i >= 0; i--) {
            if(commentList.get(i).getWriter().startsWith("익명")) {
                cnt = Long.parseLong(commentList.get(i).getWriter().substring(2)) + 1;
                break;
            }
        }

        Comment superComment = commentApiRequest.getSuperCommentId() != null ?
                getRootComment(commentApiRequest.getSuperCommentId()) : null;
        Comment comment = Comment.builder()
                .writer((commentApiRequest.getIsAnonymous() == true) ? "익명" + cnt : user.getName())
                .content(commentApiRequest.getContent())
                .isAnonymous(commentApiRequest.getIsAnonymous())
                .isDeleted(false)
                .board(board)
                .user(user)
                .superComment(superComment)
                .board(boardRepository.getOne(commentApiRequest.getBoardId()))
                .build();

        Comment newComment = commentRepository.save(comment);

        AlertApiRequest alertApiRequest = AlertApiRequest.builder()
                .commentId(newComment.getId())
                .firstCategory(board.getFirstCategory())
                .secondCategory(board.getSecondCategory())
                .postId(board.getId())
                .userId(board.getUser().getId())
                .build();
        if(comment.getContent().length() < 20) {
            alertApiRequest.setContent(comment.getContent());
        } else {
            alertApiRequest.setContent(comment.getContent().substring(0, 20));
        }
        if(user.getId() != board.getUser().getId()) {
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
