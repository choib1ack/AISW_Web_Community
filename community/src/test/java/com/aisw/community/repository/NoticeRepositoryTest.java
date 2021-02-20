package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.*;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.NoticeCategory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
public class NoticeRepositoryTest extends CommunityApplicationTests {

    // Dependency Injection (DI)
    @Autowired
    private NoticeRepository noticeRepository;

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
        User userId = userRepository.getOne(1L);

        Council council = Council.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .level(level)
                .category(NoticeCategory.COUNCIL)
                .user(userId)
                .build();

        Department department = Department.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .level(level)
                .category(NoticeCategory.DEPARTMENT)
                .user(userId)
                .build();

        University university = University.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .attachmentFile(attachmentFile)
                .status(status)
                .views(views)
                .level(level)
                .category(NoticeCategory.UNIVERSITY)
                .user(userId)
                .campus(Campus.COMMON)
                .build();

        Council newCouncil = (Council) noticeRepository.save(council);
        Department newDepartment = (Department) noticeRepository.save(department);
        University newUniversity = (University) noticeRepository.save(university);
        System.out.println("newCouncil: " + newCouncil);
        System.out.println("newDepartment: " + newDepartment);
        System.out.println("newUniversity: " + newUniversity);
    }

    @Test
    @Transactional
    public void readAll() {
        List noticeList = noticeRepository.findAll();
        noticeList.stream().forEach(o -> {
            Notice notice = (Notice) o;
            System.out.println(notice);
        });
    }
}