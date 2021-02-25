//package com.aisw.community.controller.api;
//
//import lombok.AllArgsConstructor;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController("/crawling")
//@AllArgsConstructor
//public class ContestApiController {
//    @GetMapping("")
//    public String goRegister() throws Exception{
//        Document doc = Jsoup.connect("https://www.gachon.ac.kr/community/opencampus/03.jsp?boardType_seq=358").get();
//        Elements infoList = doc.select("div[class=content]").select("div[class=boardlist]");
////        Elements contentList = doc.select("span.main-url + p");
//        for(int i = 0; i < infoList.size(); i++){
//            System.out.println(infoList.get(i).text());
//            System.out.println(infoList.get(i).attr("href"));
////            String imageUrl = doc.select("a[href=" + infoList.get(i).attr("href") + "]").select("img").attr("src");
////            System.out.println(imageUrl);
////            String content = contentList.get(i).text();
////            System.out.println(content);
//        }
//        return "views/login/register";
//    }
//}
