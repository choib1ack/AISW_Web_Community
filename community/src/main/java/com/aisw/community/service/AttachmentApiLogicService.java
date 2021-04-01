package com.aisw.community.service;

import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.Attachment;
import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.AttachmentApiResponseDTO;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.AttachmentRepository;
import com.aisw.community.repository.BulletinRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class AttachmentApiLogicService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentStorageService attachmentStorageService;

    @Autowired
    private BulletinRepository bulletinRepository;

    public ResponseEntity<?> uploadAttachment(@RequestParam("file") MultipartFile sourceFile) throws IOException {
        String sourceFileName = sourceFile.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();

        File destinationFile;
        String destinationFileName;
        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File("/Users/wonchang/desktop" + destinationFileName);
        } while (destinationFile.exists());
        destinationFile.getParentFile().mkdirs();
        sourceFile.transferTo(destinationFile);

        AttachmentApiResponseDTO response = new AttachmentApiResponseDTO();
//        response.setFileName(sourceFile.getOriginalFilename());
//        response.setFileSize(sourceFile.getSize());
        response.setFileType(sourceFile.getContentType());
//        response.setAttachmentUrl("http://localhost:8080/files/" + destinationFileName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Header<AttachmentApiResponseDTO> uploadFile(MultipartFile file){
        String fileName = attachmentStorageService.storeFile(file);
//        Bulletin bulletin = bulletinRepository.findById(1L).orElseThrow(PostNotFoundException::new);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/post")
                .path("/downloadFile")
                .path(fileName)
                .toUriString();

        Attachment attachment = Attachment.builder()
                .fileName(fileName)
                .filePath(fileDownloadUri)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .bulletin(null)
                .build();


//        attachment.setBulletin();

        attachmentRepository.save(attachment);

        return Header.OK(response(attachment));
    }

    private AttachmentApiResponseDTO response(Attachment attachment) {
        AttachmentApiResponseDTO attachmentApiResponseDTO = AttachmentApiResponseDTO.builder()
                .fileName(attachment.getFileName())
                .filePath(attachment.getFilePath())
                .fileType(attachment.getFileType())
                .fileSize(attachment.getFileSize())
                .build();

        return attachmentApiResponseDTO;
    }
}

