package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.entity.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class NoticeRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void create() {
        Long category = 1L;
        Long userId = 1L;


        Notice notice = Notice.builder()
                .category(category)
                .userId(userId)
                .build();

        Notice newNotice = noticeRepository.save(notice);
        System.out.println("newNotice: " + newNotice);
    }

    @Test
    public void read() {
        Optional<Notice> notice = noticeRepository.findById(1L);

        notice.ifPresent(readNotice -> {
            System.out.println(readNotice);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Notice> notice = noticeRepository.findById(1L);

        notice.ifPresent(readNotice -> {
            readNotice.setCategory(2L);

            noticeRepository.save(readNotice);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Notice> notice = noticeRepository.findById(1L);

        Assertions.assertTrue(notice.isPresent());

        notice.ifPresent(readNotice -> {
            noticeRepository.delete(readNotice);
        });

        Optional<Notice> deleteNotice = noticeRepository.findById(1L);

        Assertions.assertFalse(deleteNotice.isPresent());
    }
}