package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.repository.post.board.QnaRepository;
import com.aisw.community.repository.user.UserRepository;
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
        Boolean isAnonymous = true;
        String subject = "dd";
        User userId = userRepository.getOne(1L);

        Qna qna = Qna.builder()
                .title(title)
                .writer(writer)
                .content(content)
//                .attachment(attachmentFile)
                .status(status)
                .views(views)
                .likes(likes)
                .isAnonymous(isAnonymous)
                .subject(subject)
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.QNA)
                .account(userId)
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