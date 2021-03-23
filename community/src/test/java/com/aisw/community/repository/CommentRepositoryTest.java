package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Comment;
import com.aisw.community.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class CommentRepositoryTest extends CommunityApplicationTests  {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String writer = "writer";
        String content = "test Content";
        Boolean isAnonymous = true;
        Account userId = userRepository.getOne(1L);
        Board boardId = boardRepository.getOne(1L);

        Comment comment = Comment.builder()
                .writer(writer)
                .content(content)
                .isAnonymous(isAnonymous)
                .likes(0L)
                .board(boardId)
                .account(userId)
                .build();

        Comment newComment = commentRepository.save(comment);
        System.out.println("newComment: " + newComment);
    }

    @Test
    public void delete() {
        Optional<Comment> comment = commentRepository.findById(1L);

        Assertions.assertTrue(comment.isPresent());

        comment.ifPresent(readComment -> {
            commentRepository.delete(readComment);
        });

        Optional<Comment> deleteComment = commentRepository.findById(1L);

        Assertions.assertFalse(deleteComment.isPresent());
    }
}