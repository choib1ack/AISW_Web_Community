package com.aisw.community.service.post.attachment;

import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.attachment.Attachment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.response.post.attachment.AttachmentApiResponse;
import com.aisw.community.repository.post.BulletinRepository;
import com.aisw.community.repository.post.attachment.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentApiLogicService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private BulletinRepository<Bulletin> bulletinRepository;

    @Transactional
    public Header<List<AttachmentApiResponse>> uploadMultipleAttachment(MultipartFile[] files, Long id, String category) {
        List<AttachmentApiResponse> attachmentApiResponseList = Arrays.asList(files)
                .stream()
                .map(file -> uploadAttachment(file, id, category))
                .collect(Collectors.toList());

        return Header.OK(attachmentApiResponseList);
    }

    @Transactional
    public AttachmentApiResponse uploadAttachment(MultipartFile file, Long id, String category) {
         Bulletin bulletin = bulletinRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + category + "/")
                .path(fileName)
                .toUriString();

        Attachment attachment =  Attachment.builder()
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileSize(file.getSize())
                .fileType(file.getContentType())
                .bulletin(bulletin)
                .build();
        Attachment newAttachment = attachmentRepository.save(attachment);

        return response(newAttachment);
    }

    private AttachmentApiResponse response(Attachment attachment) {
        AttachmentApiResponse attachmentApiResponse = AttachmentApiResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .fileDownloadUri(attachment.getFileDownloadUri())
                .fileType(attachment.getFileType())
                .fileSize(attachment.getFileSize())
                .createdAt(attachment.getCreatedAt())
                .createdBy(attachment.getCreatedBy())
                .updatedAt(attachment.getUpdatedAt())
                .updatedBy(attachment.getUpdatedBy())
                .build();
        return attachmentApiResponse;
    }
}
