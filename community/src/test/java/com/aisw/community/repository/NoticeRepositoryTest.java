package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Notice;
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