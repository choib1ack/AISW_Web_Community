package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.UniversityContent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class UniversityContentRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private UniversityContentRepository universityContentRepository;

    @Test
    public void create() {
        String title = "test";
        String content = "test Content";
        String attachmentFile = "test attachment";
        Long status = 2L;
        Long views = 0L;
        String createdBy = "tester";
        LocalDateTime createdAt = LocalDateTime.now();

        UniversityContent universityContent = UniversityContent.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .build();

        UniversityContent newUniversityContent = universityContentRepository.save(universityContent);
        System.out.println("newUniversityContent: " + newUniversityContent);
    }

    @Test
    public void read() {
        Optional<UniversityContent> universityContent = universityContentRepository.findById(1L);

        universityContent.ifPresent(readUnivContent -> {
            System.out.println(readUnivContent);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<UniversityContent> universityContent = universityContentRepository.findById(1L);

        universityContent.ifPresent(readUnivContent -> {
            readUnivContent.setContent("Update content");
            readUnivContent.setUpdatedAt(LocalDateTime.now());
            readUnivContent.setUpdatedBy("Admin");

            universityContentRepository.save(readUnivContent);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<UniversityContent> universityContent = universityContentRepository.findById(3L);

        Assertions.assertTrue(universityContent.isPresent());

        universityContent.ifPresent(readUnivContent -> {
            universityContentRepository.delete(readUnivContent);
        });

        Optional<UniversityContent> deleteUnivContent = universityContentRepository.findById(3L);

        Assertions.assertFalse(deleteUnivContent.isPresent());
    }
}