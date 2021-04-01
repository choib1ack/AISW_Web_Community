package com.aisw.community.controller.api;

import com.aisw.community.model.entity.Attachment;
import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.AttachmentApiResponseDTO;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.AttachmentRepository;
import com.aisw.community.repository.BulletinRepository;
import com.aisw.community.service.AttachmentApiLogicService;
import com.aisw.community.service.AttachmentStorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/files")
public class AttachmentApiController {

//    private static final Logger logger = LoggerFactory.getLogger(AttachmentApiController.class);
    @Autowired
    private AttachmentStorageService attachmentStorageService;

    @Autowired
    private AttachmentApiLogicService attachmentApiLogicService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadAttachment(@RequestParam("file") MultipartFile file) throws IOException {
        String sourceFileName = file.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;

        destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
        destinationFile = new File("/Users/wonchang/desktop/files/" + destinationFileName);

        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);

        AttachmentApiResponseDTO response = new AttachmentApiResponseDTO();
        response.setFileName(file.getOriginalFilename());
        response.setFileSize(file.getSize());
        response.setFileType(file.getContentType());
        response.setFilePath("http://localhost:8080/files/" + destinationFileName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public Header<AttachmentApiResponseDTO> uploadFile(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
//        System.out.println(request);

        return null;
//        return attachmentApiLogicService.uploadFile(file);
    }

}
