package com.aisw.community.service.post.notice;

import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.entity.post.notice.University;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.UniversityApiRequest;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.model.network.response.post.notice.UniversityApiResponse;
import com.aisw.community.repository.user.AccountRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityApiLogicService extends NoticePostService<UniversityApiRequest, NoticeResponseDTO, UniversityApiResponse, University> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Header<UniversityApiResponse> create(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();
        Account account = accountRepository.findById(universityApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(universityApiRequest.getAccountId()));
        University university = University.builder()
                .title(universityApiRequest.getTitle())
                .writer(account.getName())
                .content(universityApiRequest.getContent())
                .status(universityApiRequest.getStatus())
                .views(0L)
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
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Header<UniversityApiResponse> update(Header<UniversityApiRequest> request) {
        UniversityApiRequest universityApiRequest = request.getData();

        University university = baseRepository.findById(universityApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(universityApiRequest.getId()));

        if(university.getAccount().getId() != universityApiRequest.getAccountId()) {
            throw new NotEqualAccountException(universityApiRequest.getAccountId());
        }

        university
                .setTitle(universityApiRequest.getTitle())
                .setContent(universityApiRequest.getContent())
                .setStatus(universityApiRequest.getStatus());
        university.setCampus(universityApiRequest.getCampus());
        baseRepository.save(university);

        return Header.OK(response(university));
    }

    @Override
    public Header delete(Long id, Long userId) {
        University university = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (university.getAccount().getId() != userId) {
            throw new NotEqualAccountException(userId);
        }

        baseRepository.delete(university);
        return Header.OK();
    }

//    @Override
//    public void crawling(Long boardNo) throws IOException{
//        Document doc = Jsoup.connect("https://www.gachon.ac.kr/community/opencampus/03.jsp?mode=view&boardType_seq=358&board_no=" + boardNo.toString()).get();
//        Elements elements = doc.select("table.view").select("tbody").select("tr");
//
//        String title = elements.get(0).select("td").text();
//        String writer = elements.get(1).select("td").get(0).text();
//        String createdAt = elements.get(2).select("td").text();
//        String campus = elements.get(3).select("td").text();
//        Elements files = elements.get(4).select("td a");
//        String contentHtml = elements.get(elements.size() - 1).toString();

//        System.out.println(title);
//        System.out.println(writer);
//        System.out.println(createdAt);
//        System.out.println(campus);
//        for(Element file : files){
//            System.out.println(file.text());
//            System.out.println("gachon.ac.kr" + file.attr("href"));
//        }
//        System.out.println(contentHtml);
//
//        Campus univ;
//        if(campus.equals("공통"))
//            univ = Campus.COMMON;
//        else if(campus.equals("글로벌"))
//            univ = Campus.GLOBAL;
//        else
//            univ = Campus.MEDICAL;
//
//        Account account = accountRepository.findById(1L).orElseThrow(() -> new UserNotFoundException(1L));
//        University university = University.builder()
//                .title(title)
//                .writer(writer)
//                .content(contentHtml)
//                .status(BulletinStatus.GENERAL)
//                .views(0L)
//                .campus(univ)
//                .firstCategory(FirstCategory.NOTICE)
//                .secondCategory(SecondCategory.UNIVERSITY)
//                .account(account)
//                .build();
//
//        University newUniversity = baseRepository.save(university);
//
//        List<Attachment> attachmentList = new ArrayList<>();
//        for(Element file : files){
//            Attachment attachment = Attachment.builder()
//                    .originFileName(file.text())
//                    .fileName(file.text())
//                    .filePath("gachon.ac.kr" + file.attr("href"))
//                    .fileType(file.text())
//                    .fileSize(1L)
//                    .bulletin(newUniversity)
//                    .build();
//
//            attachmentList.add(attachment);
//            attachmentRepository.save(attachment);
//        }
//    }

//    @Override
//    public Header<UniversityApiResponse> write(MultipartFile[] files) throws IOException{
//        Account account = accountRepository.findById(1L).orElseThrow(UserNotFoundException::new);
//        University university = University.builder()
//                .title("Test01")
//                .writer("Test01")
//                .content("Content01")
//                .status(BulletinStatus.GENERAL)
//                .views(0L)
//                .campus(Campus.GLOBAL)
//                .firstCategory(FirstCategory.NOTICE)
//                .secondCategory(SecondCategory.UNIVERSITY)
//                .account(account)
//                .build();
//
//        University newUniversity = baseRepository.save(university);
//
//        List<Attachment> attachmentList = new ArrayList<>();
//        for(MultipartFile file : files) {
//            File destinationFile;
//            String fileName = file.getOriginalFilename();
//            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase();
//            String destinationFileName;
//
//            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension;
//            destinationFile = new File("/Users/wonchang/desktop/files/" + destinationFileName);
//            destinationFile.getParentFile().mkdirs();
//            file.transferTo(destinationFile);
//
//            Attachment attachment = Attachment.builder()
//                    .originFileName(fileName)
//                    .fileName(destinationFileName)
//                    .filePath(destinationFile.getAbsolutePath())
//                    .fileType(file.getContentType())
//                    .fileSize(file.getSize())
//                    .bulletin(newUniversity)
//                    .build();
//
//            Attachment newAttachment = attachmentRepository.save(attachment);
//            attachmentList.add(newAttachment);
//        }
//        try {
//            fileName = new MD5Generator(file.getOriginalFilename()).toString();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        destinationFile = new File("/Users/wonchang/desktop/files/" + fileName);
//        destinationFile.getParentFile().mkdirs();
//        file.transferTo(destinationFile);
//
//        return Header.OK(response(newUniversity));
//    }

//    @Override
//    public ResponseEntity<Resource> download(Long id, String originFileName) throws IOException{
//        University university = universityRepository.findById(id).orElseThrow(UserNotFoundException::new);
//
//        Set<Attachment> attachmentList = university.getAttachment();
//
//        for(Attachment attachment : attachmentList){
//            if(attachment.getOriginFileName().equals(originFileName)){
//                System.out.println("=============== Same ===============");
//                System.out.println("Original File Name : " + attachment.getOriginFileName());
//                System.out.println("Stored File Name : " + attachment.getFileName());
//                System.out.println("File Type : " + attachment.getFileType());
//                System.out.println("File Path : " + attachment.getFilePath());
//                System.out.println("File Size : " + attachment.getFileSize());
//
//                Path filePath = Paths.get(attachment.getFilePath()).toAbsolutePath().normalize();
//                Resource resource = new UrlResource(filePath.toUri());
//
//                return ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getOriginFileName() + "\"")
//                        .body(resource);
//            }
//        }
//
//        return null;
//    }

    private UniversityApiResponse response(University university) {
        UniversityApiResponse universityApiResponse = UniversityApiResponse.builder()
                .id(university.getId())
                .title(university.getTitle())
                .writer(university.getWriter())
                .content(university.getContent())
                .status(university.getStatus())
                .views(university.getViews())
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
