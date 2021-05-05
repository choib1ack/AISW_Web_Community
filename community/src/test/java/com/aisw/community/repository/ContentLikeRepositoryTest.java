package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ContentLikeRepositoryTest extends CommunityApplicationTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Test
    public void create() {
        Account account = accountRepository.getOne(1L);
        Board board = boardRepository.getOne(2L);
        Comment comment = commentRepository.getOne(1L);

        ContentLike contentLike = ContentLike.builder()
                .account(account)
                .board(board)
//                .comment(comment)
                .build();
        ContentLike newContentLike = contentLikeRepository.save(contentLike);
        System.out.println(newContentLike);
    }

    @Test
    public void read() {
        List<ContentLike> contentLikeList = contentLikeRepository.findAllByAccountId(1L);
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
