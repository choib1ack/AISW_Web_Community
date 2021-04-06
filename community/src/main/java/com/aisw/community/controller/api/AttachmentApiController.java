package com.aisw.community.controller.api;

import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.AttachmentApiResponseDTO;
import com.aisw.community.repository.AttachmentRepository;
import com.aisw.community.service.AttachmentApiLogicService;
import com.aisw.community.service.AttachmentStorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
