package com.aisw.community.service;

import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.Attachment;
import com.aisw.community.model.entity.Bulletin;
import com.aisw.community.model.entity.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.UniversityApiRequest;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.NoticeResponseDTO;
import com.aisw.community.model.network.response.UniversityApiResponse;
import com.aisw.community.repository.AccountRepository;
import com.aisw.community.repository.AttachmentRepository;
import com.aisw.community.repository.UniversityRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UniversityApiLogicService extends NoticePostService<UniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Header<UniversityApiResponse> create(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();
        Account account = accountRepository.findById(universityApiRequest.getAccountId()).orElseThrow(UserNotFoundException::new);
        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .writer(account.getName())
                .content(universityApiRequest.getContent())
                .status(universityApiRequest.getStatus())
                .views(0L)
                .level(universityApiRequest.getLevel())
                .campus(universityApiRequest.getCampus())
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.UNIVERSITY)
                .account(account)
                .build();

        University newUniversity = baseRepository.save(university);
        return Header.OK(response(newUniversity));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(university -> university.setViews(university.getViews() + 1))
                .map(university -> baseRepository.save((University) university))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UniversityApiResponse> update(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        return baseRepository.findById(universityApiRequest.getId())
                .map(university -> {
                    university
                            .setTitle(universityApiRequest.getTitle())
                            .setContent(universityApiRequest.getContent())
                            .setStatus(universityApiRequest.getStatus())
                            .setLevel(universityApiRequest.getLevel());
                    university.setCampus(universityApiRequest.getCampus());
                    return university;
                })
                .map(university -> baseRepository.save(university))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(university -> {
                    baseRepository.delete(university);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public void crawling() throws Exception {


    }

    @Override
    public Header<UniversityApiResponse> write(MultipartFile file) throws IOException{
        Account account = accountRepository.findById(1L).orElseThrow(UserNotFoundException::new);

        File destinationFile;
        String fileName = file.getOriginalFilename();
        String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        String destinationFileName;

        destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
        destinationFile = new File("/Users/wonchang/desktop/files/" + destinationFileName);
        destinationFile.getParentFile().mkdirs();
        file.transferTo(destinationFile);

//        try {
//            fileName = new MD5Generator(file.getOriginalFilename()).toString();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        destinationFile = new File("/Users/wonchang/desktop/files/" + fileName);
//        destinationFile.getParentFile().mkdirs();
//        file.transferTo(destinationFile);

        University university = University.builder()
                .title("Test01")
                .writer("Test01")
                .content("Content01")
                .status(BulletinStatus.GENERAL)
                .views(0L)
                .level(1L)
                .campus(Campus.GLOBAL)
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.UNIVERSITY)
                .account(account)
                .build();

        University newUniversity = baseRepository.save(university);

        Attachment attachment = Attachment.builder()
                .originFileName(fileName)
                .fileName(destinationFileName)
                .filePath(destinationFile.getAbsolutePath())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .bulletin(newUniversity)
                .build();

        List<Attachment> attachmentList = new ArrayList<>();

        Attachment newAttachment = attachmentRepository.save(attachment);
        attachmentList.add(newAttachment);

        return Header.OK(response(newUniversity));
    }

    @Override
    public ResponseEntity<Resource> download(Long id, String originFileName) throws IOException{
        University university = universityRepository.findById(id).orElseThrow(UserNotFoundException::new);

        List<Attachment> attachmentList = university.getAttachment();

        for(Attachment attachment : attachmentList){
            if(attachment.getOriginFileName().equals(originFileName)){
                System.out.println("=============== Same ===============");
                System.out.println("Original File Name : " + attachment.getOriginFileName());
                System.out.println("Stored File Name : " + attachment.getFileName());
                System.out.println("File Type : " + attachment.getFileType());
                System.out.println("File Path : " + attachment.getFilePath());
                System.out.println("File Size : " + attachment.getFileSize());

                Path filePath = Paths.get(attachment.getFilePath()).toAbsolutePath().normalize();
                Resource resource = new UrlResource(filePath.toUri());

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                        .body(resource);
            }
        }

        return null;
    }

    private UniversityApiResponse response(University university) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .writer(university.getWriter())
                .content(university.getContent())
                .status(university.getStatus())
                .views(university.getViews())
                .level(university.getLevel())
                .campus(university.getCampus())
                .category(university.getCategory())
                .createdAt(university.getCreatedAt())
                .createdBy(university.getCreatedBy())
                .updatedAt(university.getUpdatedAt())
                .updatedBy(university.getUpdatedBy())
                .accountId(university.getAccount().getId())
                .build();

        return universityApiResponse;
    }

    @Override
    public Header<NoticeResponseDTO> search(Pageable pageable) {
        Page<University> universities = baseRepository.findAll(pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByWriterContaining(writer, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<University> universities = universityRepository.findAllByTitleContaining(title, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<University> universities = universityRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<University> universitiesByStatus = searchByStatus(pageable);

        return getListHeader(universities, universitiesByStatus);
    }

    public Page<University> searchByStatus(Pageable pageable) {
        Page<University> universities = universityRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return universities;
    }

    private Header<NoticeResponseDTO> getListHeader
            (Page<University> universities, Page<University> universitiesByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(universities.stream()
                        .map(notice -> NoticeApiResponse.builder()
                                .id(notice.getId())
                                .title(notice.getTitle())
                                .category(notice.getCategory())
                                .createdAt(notice.getCreatedAt())
                                .status(notice.getStatus())
                                .views(notice.getViews())
                                .writer(notice.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<NoticeApiResponse> noticeApiNoticeResponseList = new ArrayList<>();
        List<NoticeApiResponse> noticeApiUrgentResponseList = new ArrayList<>();
        universitiesByStatus.stream().forEach(notice -> {
            NoticeApiResponse noticeApiResponse = NoticeApiResponse.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .category(notice.getCategory())
                    .createdAt(notice.getCreatedAt())
                    .status(notice.getStatus())
                    .views(notice.getViews())
                    .writer(notice.getWriter())
                    .build();
            if(noticeApiResponse.getStatus() == BulletinStatus.NOTICE) {
                noticeApiNoticeResponseList.add(noticeApiResponse);
            }
            else if(noticeApiResponse.getStatus() == BulletinStatus.URGENT) {
                noticeApiUrgentResponseList.add(noticeApiResponse);
            }
        });
        noticeResponseDTO.setNoticeApiNoticeResponseList(noticeApiNoticeResponseList);
        noticeResponseDTO.setNoticeApiUrgentResponseList(noticeApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(universities.getTotalElements())
                .totalPages(universities.getTotalPages())
                .currentElements(universities.getNumberOfElements())
                .currentPage(universities.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }
}
