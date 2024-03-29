package com.aisw.community.service.post.file;

import com.aisw.community.component.advice.exception.BannerNotFoundException;
import com.aisw.community.component.advice.exception.CanNotDetermineFileTypeException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.SiteInformationNotFoundException;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

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
    public List<FileApiResponse> uploadFiles(MultipartFile[] multipartFiles, String username, String prefix, Long id, UploadCategory category) {
        return Arrays.asList(multipartFiles)
                .stream()
                .map(file -> upload(file, username, prefix, id, category))
                .map(file -> response(file))
                .collect(Collectors.toList());
    }

    @Transactional
    public File upload(MultipartFile multipartFile, String username, String prefix, Long id, UploadCategory category) {
        String[] storeFile = fileStorageService.storeFile(multipartFile, username);
        String fileName = storeFile[0];
        String storedFileName = storeFile[1];
        String defaultPrefix = "/api";
        String defaultSuffix = "/file/download/";
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path((prefix != null) ? defaultPrefix + prefix + defaultSuffix : defaultPrefix + defaultSuffix)
                .path(storedFileName)
                .toUriString();

        File file = File.builder()
                .fileName(fileName)
                .storedFileName(storedFileName)
                .fileDownloadUri(fileDownloadUri)
                .fileSize(multipartFile.getSize())
                .fileType(multipartFile.getContentType())
                .build();
        if (category.getTitle().equals("post")) {
            Bulletin bulletin = bulletinRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
            file.setBulletin(bulletin);
        } else if (category.getTitle().equals("banner")) {
            Banner banner = bannerRepository.findById(id).orElseThrow(() -> new BannerNotFoundException(id));
            file.setBanner(banner);
        } else if (category.getTitle().equals("site")) {
            SiteInformation siteInformation = siteInformationRepository.findById(id)
                    .orElseThrow(() -> new SiteInformationNotFoundException(id));
            file.setSiteInformation(siteInformation);
        }
        File newFile = fileRepository.save(file);

        return newFile;
    }

    @Transactional
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

    public List<FileApiResponse> getFileList(List<File> fileList) {
        return fileList.stream().map(this::response).collect(Collectors.toList());
    }

    public String getNewFileName(String fileName) {
        String prefix = fileName.substring(0, fileName.indexOf("."));
        String extension = fileName.substring(fileName.indexOf("."));
        List<File> fileList =
                fileRepository.findAllByStoredFileNameStartingWithAndStoredFileNameEndsWith(prefix, extension);
        Long index = 0L;
        for (int i = 0; i < fileList.size(); i++) {
            String number = fileList.get(i).getStoredFileName()
                    .replace(prefix, "")
                    .replace(extension, "")
                    .replace("(", "")
                    .replace(")", "");
            index = Math.max(index, Long.parseLong(number));
        }
        fileName = prefix + "(" + (index + 1) + ")" + extension;
        return fileName;
    }

    public void deleteFileList(List<File> fileList) {
        fileList.stream().forEach(file -> fileStorageService.deleteFile(file));
    }

    public void deleteFile(File file) {
        fileStorageService.deleteFile(file);
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
