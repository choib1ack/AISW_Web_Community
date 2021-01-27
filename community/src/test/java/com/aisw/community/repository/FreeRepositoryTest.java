package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class FreeRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private FreeRepository freeRepository;

    @Test
    public void create() {
        String title = "test";
        String content = "test Content";
        String attachmentFile = "test attachment";
        Long views = 0L;
        String createdBy = "tester";
        LocalDateTime createdAt = LocalDateTime.now();
        Long level = 1L;
        Long likes = 0L;

        Free free = Free.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .level(level)
                .likes(likes)
                .build();

        Free newFree = freeRepository.save(free);
        System.out.println("newFree: " + newFree);
    }

    @Test
    public void read() {
        Optional<Free> free = freeRepository.findById(1L);

        free.ifPresent(readFree -> {
            System.out.println(readFree);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Free> free = freeRepository.findById(1L);

        free.ifPresent(readFree -> {
            readFree.setLevel(2L);

            freeRepository.save(readFree);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Free> free = freeRepository.findById(1L);

        Assertions.assertTrue(free.isPresent());

        free.ifPresent(readFree -> {
            freeRepository.delete(readFree);
        });

        Optional<Free> deleteFree = freeRepository.findById(1L);

        Assertions.assertFalse(deleteFree.isPresent());
    }
}