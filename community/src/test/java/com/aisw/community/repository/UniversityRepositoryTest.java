package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.University;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UniversityRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UniversityRepository universityRepository;

    @Test
    public void create() {
        Long level = 1L;
        Long campus = 0L;
        Long universityContentId = 1L;
        Long noticeId = 1L;


        University university = University.builder()
                .level(level)
                .campus(campus)
                .universityContentId(universityContentId)
                .noticeId(noticeId)
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