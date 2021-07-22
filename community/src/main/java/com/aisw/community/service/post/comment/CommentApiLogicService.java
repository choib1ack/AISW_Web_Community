package com.aisw.community.service.post.comment;

import com.aisw.community.advice.exception.CommentNotFoundException;
import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.comment.CommentApiRequest;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.comment.CustomCommentRepository;
import com.aisw.community.repository.user.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentApiLogicService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomCommentRepository customCommentRepository;

    @Transactional
    public Header<CommentApiResponse> create(Header<CommentApiRequest> request) {
        CommentApiRequest commentApiRequest = request.getData();
        Account account = accountRepository.findById(commentApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(commentApiRequest.getAccountId()));
        Board board = boardRepository.findById(commentApiRequest.getBoardId()).orElseThrow(
                () -> new PostNotFoundException(commentApiRequest.getBoardId()));

        Comment superComment = commentApiRequest.getSuperCommentId() != null ?
                getRootComment(commentApiRequest.getSuperCommentId()) : null;
        Comment comment = Comment.builder()
                .writer(account.getName())
                .content(commentApiRequest.getContent())
                .isAnonymous(commentApiRequest.getIsAnonymous())
                .isDeleted(false)
                .board(board)
                .account(account)
                .superComment(superComment)
                .board(boardRepository.getOne(commentApiRequest.getBoardId()))
                .account(accountRepository.getOne(commentApiRequest.getAccountId()))
                .build();

        Comment newComment = commentRepository.save(comment);
        return Header.OK(response(newComment));
    }

    @Transactional
    public Header delete(Long id, Long userId) {
        Comment comment = commentRepository.findCommentByIdWithSuperComment(id).orElseThrow(
                () -> new CommentNotFoundException(id));

        if (comment.getAccount().getId() != userId) {
            throw new NotEqualAccountException(userId);
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
                .accountId(comment.getAccount().getId())
                .build();

        return freeCommentApiResponse;
    }

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
}
