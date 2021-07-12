//package com.aisw.community.service.post.file;
//
//import com.aisw.community.advice.exception.BannerNotFoundException;
//import com.aisw.community.advice.exception.PostNotFoundException;
//import com.aisw.community.advice.exception.SiteInformationNotFoundException;
//import com.aisw.community.model.entity.admin.Banner;
//import com.aisw.community.model.entity.admin.SiteInformation;
//import com.aisw.community.model.entity.post.Bulletin;
//import com.aisw.community.model.entity.post.file.File;
//import com.aisw.community.model.enumclass.UploadCategory;
//import com.aisw.community.model.network.Header;
//import com.aisw.community.model.network.response.post.file.FileApiResponse;
//import com.aisw.community.repository.admin.BannerRepository;
//import com.aisw.community.repository.admin.SiteInformationRepository;
//import com.aisw.community.repository.post.BulletinRepository;
//import com.aisw.community.repository.post.file.FileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FileApiLogicService {
//
//    @Autowired
//    private FileStorageService fileStorageService;
//
//    @Autowired
//    private FileRepository fileRepository;
//
//    @Autowired
//    private BulletinRepository<Bulletin> bulletinRepository;
//
//    @Autowired
//    private BannerRepository bannerRepository;
//
//    @Autowired
//    private SiteInformationRepository siteInformationRepository;
//
//    @Transactional
//    public Header<List<FileApiResponse>> uploadFiles(MultipartFile[] multipartFiles, Long id, UploadCategory category) {
//        List<FileApiResponse> fileApiResponseList = Arrays.asList(multipartFiles)
//                .stream()
//                .map(file -> upload(id, file, category))
//                .collect(Collectors.toList());
//
//        return Header.OK(fileApiResponseList);
//    }
//
//    @Transactional
//    public FileApiResponse upload(Long id, MultipartFile multipartFile, UploadCategory category) {
//        String fileName = fileStorageService.storeFile(multipartFile);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/" + category.getTitle() + "/")
//                .path(fileName)
//                .toUriString();
//
//        File file = File.builder()
//                .fileName(fileName)
//                .fileDownloadUri(fileDownloadUri)
//                .fileSize(multipartFile.getSize())
//                .fileType(multipartFile.getContentType())
//                .build();
//        if(category.getTitle().equals(UploadCategory.POST.getTitle())) {
//            Bulletin bulletin = bulletinRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
//            file.setBulletin(bulletin);
//
//        } else if(category.getTitle().equals(UploadCategory.BANNER.getTitle())) {
//            Banner banner = bannerRepository.findById(id).orElseThrow(() -> new BannerNotFoundException(id));
//            file.setBanner(banner);
//        } else if(category.getTitle().equals(UploadCategory.SITE.getTitle())) {
//            SiteInformation siteInformation = siteInformationRepository.findById(id).orElseThrow(() -> new SiteInformationNotFoundException(id));
//            file.setSiteInformation(siteInformation);
//        }
//        File newFile = fileRepository.save(file);
//
//        return response(newFile);
//    }
//
//    private FileApiResponse response(File file) {
//        FileApiResponse fileApiResponse = FileApiResponse.builder()
//                .id(file.getId())
//                .fileName(file.getFileName())
//                .fileDownloadUri(file.getFileDownloadUri())
//                .fileType(file.getFileType())
//                .fileSize(file.getFileSize())
//                .createdAt(file.getCreatedAt())
//                .createdBy(file.getCreatedBy())
//                .updatedAt(file.getUpdatedAt())
//                .updatedBy(file.getUpdatedBy())
//                .build();
//        return fileApiResponse;
//    }
//}

package com.aisw.community.service.post.file;

import com.aisw.community.advice.exception.*;
import com.aisw.community.model.entity.admin.Banner;
import com.aisw.community.model.entity.admin.SiteInformation;
import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.admin.BannerRepository;
import com.aisw.community.repository.admin.SiteInformationRepository;
import com.aisw.community.repository.post.BulletinRepository;
import com.aisw.community.repository.post.file.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileApiLogicService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BulletinRepository<Bulletin> bulletinRepository;

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SiteInformationRepository siteInformationRepository;

    @Transactional
    public List<FileApiResponse> uploadFiles(MultipartFile[] multipartFiles, Long id, UploadCategory category) {
        List<FileApiResponse> fileList = Arrays.asList(multipartFiles)
                .stream()
                .map(file -> upload(file, id, category))
                .map(file -> response(file))
                .collect(Collectors.toList());

        return fileList;
    }

    @Transactional
    public File upload(MultipartFile multipartFile, Long id, UploadCategory category) {
        String fileName = fileStorageService.storeFile(multipartFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/" + category.getTitle() + "/")
                .path(fileName)
                .toUriString();

        File file = File.builder()
                .fileName(fileName)
                .fileDownloadUri(fileDownloadUri)
                .fileSize(multipartFile.getSize())
                .fileType(multipartFile.getContentType())
                .build();
        if (category.getTitle().equals(UploadCategory.POST.getTitle())) {
            Bulletin bulletin = bulletinRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
            file.setBulletin(bulletin);

        } else if (category.getTitle().equals(UploadCategory.BANNER.getTitle())) {
            Banner banner = bannerRepository.findById(id).orElseThrow(() -> new BannerNotFoundException(id));
            file.setBanner(banner);
        } else if (category.getTitle().equals(UploadCategory.SITE.getTitle())) {
            SiteInformation siteInformation = siteInformationRepository.findById(id).orElseThrow(() -> new SiteInformationNotFoundException(id));
            file.setSiteInformation(siteInformation);
        }
        File newFile = fileRepository.save(file);

        return newFile;
    }

    public void delete(File file) {
        File deleteFile = fileRepository.findById(file.getId()).orElseThrow(() -> new FileNotFoundException(file.getId()));
        fileRepository.delete(deleteFile);
    }

    public ResponseEntity<Resource> download(String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new CanNotDetermineFileTypeException("content type can't be determine with file type: " + contentType, ex);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public FileApiResponse response(File file) {
        FileApiResponse fileApiResponse = FileApiResponse.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .fileDownloadUri(file.getFileDownloadUri())
                .fileSize(file.getFileSize())
                .fileType(file.getFileType())
                .createdAt(file.getCreatedAt())
                .createdBy(file.getCreatedBy())
                .updatedAt(file.getUpdatedAt())
                .updatedBy(file.getUpdatedBy())
                .build();
        return fileApiResponse;
    }
}
