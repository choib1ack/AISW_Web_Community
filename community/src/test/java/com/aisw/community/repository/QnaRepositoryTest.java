package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class QnaRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        String title = "test";
        String writer = "writer";
        String content = "test Content";
        String attachmentFile = "test attachment";
        BulletinStatus status = BulletinStatus.GENERAL;
        Long views = 0L;
        Long likes = 0L;
        Long level = 1L;
        Boolean isAnonymous = true;
        String subject = "dd";
        User userId = userRepository.getOne(1L);

        Qna qna = Qna.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .likes(likes)
                .level(level)
                .isAnonymous(isAnonymous)
                .subject(subject)
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.QNA)
                .user(userId)
                .build();

        Qna newQna = qnaRepository.save(qna);
        System.out.println("newQna: " + newQna);
    }

    @Test
    public void read() {
        Optional<Qna> qna = qnaRepository.findById(3L);

        qna.ifPresent(readQna -> System.out.println(readQna));
    }

    @Test
    @Transactional
    public void update() {
        Optional<Qna> qna = qnaRepository.findById(3L);

        qna.ifPresent(readQna -> {
            readQna.setLevel(2L);

            qnaRepository.save(readQna);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Qna> qna = qnaRepository.findById(3L);

        Assertions.assertTrue(qna.isPresent());

        qna.ifPresent(readQna -> {
            qnaRepository.delete(readQna);
        });

        Optional<Qna> deleteQna = qnaRepository.findById(3L);

        Assertions.assertFalse(deleteQna.isPresent());
    }
}