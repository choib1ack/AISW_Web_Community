//package com.aisw.community.controller.api;
//
//import com.aisw.community.controller.PostController;
//import com.aisw.community.model.entity.Qna;
//import com.aisw.community.model.network.Header;
//import com.aisw.community.model.network.request.QnaApiRequest;
//import com.aisw.community.model.network.response.FreeApiResponse;
//import com.aisw.community.model.network.response.QnaApiResponse;
//import com.aisw.community.service.QnaApiLogicService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequestMapping("/board/qna")
//public class QnaApiController extends PostController<QnaApiRequest, QnaApiResponse, Qna> {
//
//    @Autowired
//    private QnaApiLogicService qnaApiLogicService;
//
//    @GetMapping("/likes/{id}")
//    public Header<QnaApiResponse> pressLikes(@PathVariable Long id) {
//        return qnaApiLogicService.pressLikes(id);
//    }
//}
