package com.aisw.community.controller.api.post.attachment;

import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.post.attachment.AttachmentApiResponse;
import com.aisw.community.service.post.attachment.AttachmentApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/attachment")
public class AttachmentApiController {

    @Autowired
    private AttachmentApiLogicService attachmentApiLogicService;

    @PostMapping("/upload/{id}")
    public Header<List<AttachmentApiResponse>> upload(@RequestParam("file") MultipartFile[] files,
                                                      @PathVariable Long id, @RequestParam String category) {
        return attachmentApiLogicService.uploadFiles(files, id, category);
    }
}