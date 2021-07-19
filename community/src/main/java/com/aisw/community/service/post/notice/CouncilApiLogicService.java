package com.aisw.community.service.post.notice;

import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.post.notice.Council;
import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.notice.CouncilApiRequest;
import com.aisw.community.model.network.request.post.notice.FileUploadToCouncilDTO;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.model.network.response.post.notice.CouncilApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeApiResponse;
import com.aisw.community.model.network.response.post.notice.NoticeResponseDTO;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.notice.CouncilRepository;
import com.aisw.community.repository.user.AccountRepository;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouncilApiLogicService extends NoticePostService<CouncilApiRequest, FileUploadToCouncilDTO, NoticeResponseDTO, CouncilApiResponse, Council> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CouncilRepository councilRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Override
    public Header<CouncilApiResponse> create(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();
        Account account = accountRepository.findById(councilApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(councilApiRequest.getAccountId()));
        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .writer(account.getName())
                .content(councilApiRequest.getContent())
                .status(councilApiRequest.getStatus())
                .views(0L)
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.COUNCIL)
                .account(account)
                .build();

        Council newCouncil = baseRepository.save(council);
        return Header.OK(response(newCouncil));
    }

    @Override
    @Transactional
    public Header<CouncilApiResponse> create(FileUploadToCouncilDTO request) {
        CouncilApiRequest councilApiRequest = request.getCouncilApiRequest();

        Account account = accountRepository.findById(councilApiRequest.getAccountId()).orElseThrow(
                () -> new UserNotFoundException(councilApiRequest.getAccountId()));
        Council council = Council.builder()
                .title(councilApiRequest.getTitle())
                .writer(account.getName())
                .content(councilApiRequest.getContent())
                .status(councilApiRequest.getStatus())
                .views(0L)
                .firstCategory(FirstCategory.NOTICE)
                .secondCategory(SecondCategory.COUNCIL)
                .account(account)
                .build();
        Council newCouncil = baseRepository.save(council);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newCouncil.getId(), UploadCategory.POST);

        return Header.OK(response(newCouncil, fileApiResponseList));
    }

    @Override
    @Transactional
    public Header<CouncilApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(council -> council.setViews(council.getViews() + 1))
                .map(council -> baseRepository.save((Council)council))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    public Header<CouncilApiResponse> update(Header<CouncilApiRequest> request) {
        CouncilApiRequest councilApiRequest = request.getData();

        Council council = baseRepository.findById(councilApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(councilApiRequest.getId()));

        if(council.getAccount().getId() != councilApiRequest.getAccountId()) {
            throw new NotEqualAccountException(councilApiRequest.getAccountId());
        }

        council
                .setTitle(councilApiRequest.getTitle())
                .setContent(councilApiRequest.getContent())
                .setStatus(councilApiRequest.getStatus());
        baseRepository.save(council);

        return Header.OK(response(council));
    }

    @Override
    @Transactional
    public Header<CouncilApiResponse> update(FileUploadToCouncilDTO request) {
        CouncilApiRequest councilApiRequest = request.getCouncilApiRequest();
        MultipartFile[] files = request.getFiles();

        Council council = baseRepository.findById(councilApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(councilApiRequest.getId()));

        if(council.getAccount().getId() != councilApiRequest.getAccountId()) {
            throw new NotEqualAccountException(councilApiRequest.getAccountId());
        }

        council.getFileList().stream().forEach(file -> fileRepository.delete(file));
        council.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, council.getId(), UploadCategory.POST);

        council
                .setTitle(councilApiRequest.getTitle())
                .setContent(councilApiRequest.getContent())
                .setStatus(councilApiRequest.getStatus());
        baseRepository.save(council);

        return Header.OK(response(council, fileApiResponseList));
    }

    @Override
    public Header delete(Long id, Long userId) {
        Council council = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (council.getAccount().getId() != userId) {
            throw new NotEqualAccountException(userId);
        }

        baseRepository.delete(council);
        return Header.OK();
    }

    private CouncilApiResponse response(Council council) {
        CouncilApiResponse councilApiResponse = CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .status(council.getStatus())
                .views(council.getViews())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .accountId(council.getAccount().getId())
                .fileApiResponseList(council.getFileList().stream()
                        .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()))
                .build();

        return councilApiResponse;
    }

    private CouncilApiResponse response(Council council, List<FileApiResponse> fileApiResponseList) {
        CouncilApiResponse councilApiResponse = CouncilApiResponse.builder()
                .id(council.getId())
                .title(council.getTitle())
                .writer(council.getWriter())
                .content(council.getContent())
                .status(council.getStatus())
                .views(council.getViews())
                .category(council.getCategory())
                .createdAt(council.getCreatedAt())
                .createdBy(council.getCreatedBy())
                .updatedAt(council.getUpdatedAt())
                .updatedBy(council.getUpdatedBy())
                .accountId(council.getAccount().getId())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return councilApiResponse;
    }

    @Override

    @Cacheable(value = "councilSearch", key = "#pageable.pageNumber")

    public Header<NoticeResponseDTO> search(Pageable pageable) {
        Page<Council> councils = baseRepository.findAll(pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByWriterContaining(writer, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByTitleContaining(title, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    @Override
    public Header<NoticeResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Council> councils = councilRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Council> councilsByStatus = searchByStatus(pageable);

        return getListHeader(councils, councilsByStatus);
    }

    public Page<Council> searchByStatus(Pageable pageable) {
        Page<Council> councils = councilRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return councils;
    }

    private Header<NoticeResponseDTO> getListHeader
            (Page<Council> councils, Page<Council> councilsByStatus) {
        NoticeResponseDTO noticeResponseDTO = NoticeResponseDTO.builder()
                .noticeApiResponseList(councils.stream()
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
        councilsByStatus.stream().forEach(notice -> {
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
                .totalElements(councils.getTotalElements())
                .totalPages(councils.getTotalPages())
                .currentElements(councils.getNumberOfElements())
                .currentPage(councils.getNumber())
                .build();

        return Header.OK(noticeResponseDTO, pagination);
    }
}
