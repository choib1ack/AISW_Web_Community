package com.aisw.community.repository;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.repository.post.notice.UniversityRepository;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.service.post.notice.UniversityApiLogicService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class UniversityRepositoryTest extends CommunityApplicationTests {
    @Autowired
    UniversityApiLogicService universityApiLogicService;

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
        User userId = userRepository.getOne(1L);


        University university = University.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .status(status)
                .views(views)
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

    @Test
    @Transactional
    public void crawl() throws Exception {
        Document doc = Jsoup.connect("https://www.gachon.ac.kr/community/opencampus/03.jsp?mode=view&boardType_seq=358&board_no=10327").get();
        Elements elements = doc.select("table.view").select("tbody").select("tr");

        String title = elements.get(0).select("td").text();
        String writer = elements.get(1).select("td").get(0).text();
        String createdAt = elements.get(2).select("td").text();
        String campus = elements.get(3).select("td").text();
        Elements files = elements.get(4).select("td a");

        System.out.println(title);
        System.out.println(writer);
        System.out.println(createdAt);
        System.out.println(campus);
        for(Element file : files){
            System.out.println(file.text());
            System.out.println("gachon.ac.kr" + file.attr("href"));
        }
        System.out.println(elements.get(elements.size() - 1));
    }
}