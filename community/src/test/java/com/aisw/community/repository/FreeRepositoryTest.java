package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public class FreeRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void create() {
        String title = "test";
        String writer = "writer";
        String content = "test Content";
        String attachmentFile = "test attachment";
        BulletinStatus status = BulletinStatus.NOTICE;
        Long views = 0L;
        Long likes = 0L;
        Long level = 1L;
        Boolean isAnonymous = true;
        Account userId = accountRepository.getOne(1L);

        Free free = Free.builder()
                .title(title)
                .writer(writer)
                .content(content)
//                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .likes(likes)
                .level(level)
                .isAnonymous(isAnonymous)
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .account(userId)
                .build();

        Free newFree = freeRepository.save(free);
        System.out.println("newFree: " + newFree);
    }

    @Test
    public void read() {
        Optional<Free> free = freeRepository.findById(1L);

        free.ifPresent(readFree -> System.out.println(readFree));
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