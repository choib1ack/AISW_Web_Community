package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import com.aisw.community.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ContentLikeRepositoryTest extends CommunityApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Test
    public void create() {
        User user = userRepository.getOne(1L);
        Board board = boardRepository.getOne(2L);
        Comment comment = commentRepository.getOne(1L);

        ContentLike contentLike = ContentLike.builder()
                .user(user)
                .board(board)
//                .comment(comment)
                .build();
        ContentLike newContentLike = contentLikeRepository.save(contentLike);
        System.out.println(newContentLike);
    }

    @Test
    public void read() {
        List<ContentLike> contentLikeList = contentLikeRepository.findAllByUserId(1L);
        contentLikeList.stream().forEach(System.out::println);
    }

    @Test
    public void delete() {
        Optional<ContentLike> contentLike = contentLikeRepository.findById(1L);

        Assertions.assertTrue(contentLike.isPresent());

        contentLike.ifPresent(readCouncil -> {
            contentLikeRepository.delete(readCouncil);
        });

        Optional<ContentLike> deleteContentLike = contentLikeRepository.findById(1L);

        Assertions.assertFalse(deleteContentLike.isPresent());
    }
}
