package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Council;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.enumclass.BulletinStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class CouncilRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private CouncilRepository councilRepository;

    @Test
    public void create() {
        String title = "test";
        String content = "test Content";
        String attachmentFile = "test attachment";
        BulletinStatus status = BulletinStatus.GENERAL;
        Long views = 0L;
        String createdBy = "tester";
        LocalDateTime createdAt = LocalDateTime.now();
        Long level = 1L;

        Council council = Council.builder()
                .title(title)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .level(level)
                .build();

        Council newCouncil = councilRepository.save(council);
        System.out.println("newCouncil: " + newCouncil);
    }

    @Test
    public void read() {
        Optional<Council> council = councilRepository.findById(1L);

        council.ifPresent(readCouncil -> {
            System.out.println(readCouncil);
        });
    }

    @Test
    @Transactional
    public void update() {
        Optional<Council> council = councilRepository.findById(1L);

        council.ifPresent(readCouncil -> {
            readCouncil.setLevel(2L);

            councilRepository.save(readCouncil);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Council> council = councilRepository.findById(1L);

        Assertions.assertTrue(council.isPresent());

        council.ifPresent(readCouncil -> {
            councilRepository.delete(readCouncil);
        });

        Optional<Council> deleteCouncil = councilRepository.findById(1L);

        Assertions.assertFalse(deleteCouncil.isPresent());
    }
}