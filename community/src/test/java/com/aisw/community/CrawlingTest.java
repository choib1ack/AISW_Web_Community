package com.aisw.community;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class CrawlingTest extends CommunityApplicationTests {
    @Test
    public void getPageUrl() throws Exception{
        String pageUrl = "https://www.gachon.ac.kr/community/opencampus/03.jsp?pageNum=0&boardType_seq=358";
        Document doc = Jsoup.connect(pageUrl).get();

        // 페이지 번호 클릭 시 이동하는 url 담아둔 객체
        Elements btnList = doc.select("div[class=page]").select("a");
        for(Element e : btnList){
            String numUrl = e.attr("href");
            System.out.println("#" + e.text() + " page url : " + numUrl);
        }
    }

    @Test
    public void getNoticeUrl() throws Exception{
        String pageUrl = "https://www.gachon.ac.kr/community/opencampus/03.jsp?pageNum=0&boardType_seq=358";
        Document doc = Jsoup.connect(pageUrl).get();

        // 각 게시글 제목, url 담아둔 객체
        Elements noticeList = doc.select("div[class=boardlist]").select("tr td.tl").select("a");
        for(Element e : noticeList){
            String postUrl = e.attr("href");
            System.out.println("Title : " + e.text() + "\n url : " + postUrl);
        }
    }

    @Test
    public void getNoticeContent() throws Exception{
        String pageUrl = "https://www.gachon.ac.kr/community/opencampus/03.jsp?mode=view&boardType_seq=358&board_no=10161&pageNum=0";
        Document doc = Jsoup.connect(pageUrl).get();

        // 게시글 정보 담아둔 객체
        Elements noticeInfo = doc.select("table[class=view").select("tr td");
        String title = noticeInfo.get(0).text();
        String createdBy = noticeInfo.get(1).text();
        String createdAt = noticeInfo.get(3).text();
        String status = noticeInfo.get(4).text();
        Element attInfo = noticeInfo.get(5);
        String attTitle = attInfo.text();
        String attUrl = attInfo.select("a").attr("href");
        String contentHtml = noticeInfo.last().outerHtml();

        System.out.println("Title : " + title);
        System.out.println("Created by : " + createdBy);
        System.out.println("Created at : " + createdAt);
        System.out.println("Status : " + status);
        System.out.println("Attachment Title : " + attTitle);
        System.out.println("Attachment Url : " + attUrl);
        System.out.println(contentHtml);
    }
}
