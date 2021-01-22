package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UniversityRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UniversityRepository universityRepository;

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
        Long campus = 0L;

        University university = University.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .level(level)
                .campus(campus)
                .build();

        University newUniversity = universityRepository.save(university);
        System.out.println("newUniversity: " + newUniversity);
    }

    @Test
    public void read() {
        Optional<University> university = universityRepository.findById(1L);

        university.ifPresent(readUniversity -> {
            System.out.println(readUniversity);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<University> university = universityRepository.findById(1L);

        university.ifPresent(readUniversity -> {
            readUniversity.setLevel(2L);
            readUniversity.setCampus(1L);

            universityRepository.save(readUniversity);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<University> university = universityRepository.findById(1L);

        Assertions.assertTrue(university.isPresent());

        university.ifPresent(readUniversity -> {
            universityRepository.delete(readUniversity);
        });

        Optional<University> deleteUnivContent = universityRepository.findById(1L);

        Assertions.assertFalse(deleteUnivContent.isPresent());
    }
}