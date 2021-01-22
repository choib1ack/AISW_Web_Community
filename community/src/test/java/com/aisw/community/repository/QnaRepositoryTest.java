package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.Qna;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class QnaRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private QnaRepository qnaRepository;

    @Test
    public void create() {
        String title = "test";
        String content = "test Content";
        String attachmentFile = "test attachment";
        Long status = 2L;
        Long views = 0L;
        String createdBy = "tester";
        LocalDateTime createdAt = LocalDateTime.now();
        Long level = 1L;
        Long likes = 0L;
        String subject = "OS";

        Qna free = Qna.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .level(level)
                .likes(likes)
                .subject(subject)
                .build();

        Qna newQna = qnaRepository.save(free);
        System.out.println("newQna: " + newQna);
    }

    @Test
    public void read() {
        Optional<Qna> qna = qnaRepository.findById(1L);

        qna.ifPresent(readQna -> {
            System.out.println(readQna);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Qna> qna = qnaRepository.findById(1L);

        qna.ifPresent(readQna -> {
            readQna.setLevel(2L);

            qnaRepository.save(readQna);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Qna> qna = qnaRepository.findById(1L);

        Assertions.assertTrue(qna.isPresent());

        qna.ifPresent(readQna -> {
            qnaRepository.delete(readQna);
        });

        Optional<Qna> deleteQna = qnaRepository.findById(1L);

        Assertions.assertFalse(deleteQna.isPresent());
    }
}