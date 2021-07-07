//package com.aisw.community.controller.api.post.file;
//
//import com.aisw.community.model.enumclass.UploadCategory;
//import com.aisw.community.model.network.Header;
//import com.aisw.community.model.network.response.post.file.FileApiResponse;
//import com.aisw.community.service.post.file.FileApiLogicService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/file")
//public class FileApiController {
//
//    @Autowired
//    private FileApiLogicService fileApiLogicService;
//
//    @PostMapping("/upload/{id}")
//    public Header<List<FileApiResponse>> upload(@RequestParam("file") MultipartFile[] files,
//                                                @PathVariable Long id, @RequestParam("category") UploadCategory category) {
//        return fileApiLogicService.uploadFiles(files, id, category);
//    }
//}