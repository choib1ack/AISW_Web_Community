package com.aisw.community.service;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.advice.exception.CommentNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.network.request.post.like.ContentLikeApiRequest;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ContentLikeApiLogicServiceTest extends CommunityApplicationTests {

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void createLike() {
        ContentLikeApiRequest contentLikeApiRequest = ContentLikeApiRequest.builder()
                .accountId(1L)
//                .boardId(1L)
                .commentId(7L)
                .build();

        User user = userRepository.findById(contentLikeApiRequest.getAccountId()).orElseThrow(UserNotFoundException::new);
//        Board board = boardRepository.findById(contentLikeApiRequest.getBoardId()).orElseThrow(PostNotFoundException::new);
        Comment comment = commentRepository.findById(contentLikeApiRequest.getCommentId()).orElseThrow(CommentNotFoundException::new);

//        contentLikeRepository.findContentLikeByAccountIdAndBoardId(1L, 3L)
//                .ifPresent(contentLike -> {throw new RuntimeException();});
        contentLikeRepository.findContentLikeByUserIdAndCommentId(1L, 7L)
                .ifPresent(contentLike -> {throw new RuntimeException();});

        ContentLike contentLike = ContentLike.builder()
                .user(user)
//                .board(board)
                .comment(comment)
                .build();

        ContentLike newContentLike = contentLikeRepository.save(contentLike);
        System.out.println(newContentLike);
    }

    @Test
    public void deleteLike() {
        ContentLikeApiRequest contentLikeApiRequest = ContentLikeApiRequest.builder()
                .accountId(1L)
//                .boardId(1L)
                .commentId(7L)
                .build();

        User user = userRepository.findById(contentLikeApiRequest.getAccountId()).orElseThrow(UserNotFoundException::new);
//        Board board = boardRepository.findById(contentLikeApiRequest.getBoardId()).orElseThrow(PostNotFoundException::new);
        Comment comment = commentRepository.findById(contentLikeApiRequest.getCommentId()).orElseThrow(CommentNotFoundException::new);

//        contentLikeRepository.findContentLikeByAccountIdAndBoardId(1L, 3L).orElseThrow(RuntimeException::new);
        ContentLike deleteContentLike = contentLikeRepository.findContentLikeByUserIdAndCommentId(1L, 7L).orElseThrow(RuntimeException::new);
        contentLikeRepository.delete(deleteContentLike);
    }
}