package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UniversityRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UniversityRepository universityRepository;

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
        Long level = 1L;
        Account userId = userRepository.getOne(1L);


        University university = University.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .level(level)
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.UNIVERSITY)
                .account(userId)
                .campus(Campus.COMMON)
                .build();

        University newUniversity = universityRepository.save(university);
        System.out.println("newUniversity: " + newUniversity);
    }

    @Test
    public void read() {
        Optional<University> university = universityRepository.findById(3L);

        university.ifPresent(readUniversity -> {
            System.out.println(readUniversity);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<University> university = universityRepository.findById(3L);

        university.ifPresent(readUniversity -> {
            readUniversity.setLevel(2L);

            universityRepository.save(readUniversity);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<University> university = universityRepository.findById(3L);

        Assertions.assertTrue(university.isPresent());

        university.ifPresent(readUniversity -> {
            universityRepository.delete(readUniversity);
        });

        Optional<University> deleteUniversity = universityRepository.findById(3L);

        Assertions.assertFalse(deleteUniversity.isPresent());
    }
}