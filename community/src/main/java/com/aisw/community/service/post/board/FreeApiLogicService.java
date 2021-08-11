package com.aisw.community.service.post.board;

import com.aisw.community.advice.exception.NotEqualUserException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.FileUploadToFreeApiRequest;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.FreeRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.service.post.comment.CommentApiLogicService;
import com.aisw.community.service.post.file.FileApiLogicService;
import com.aisw.community.service.post.like.ContentLikeApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeApiLogicService extends BoardPostService<FreeApiRequest, FileUploadToFreeApiRequest, BoardResponseDTO, FreeDetailApiResponse, FreeApiResponse, Free> {

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Autowired
    private ContentLikeApiLogicService contentLikeApiLogicService;

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "freeReadAll", allEntries = true),
//            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "boardReadAll", allEntries = true),
//            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "home", allEntries = true)
//    })
    public Header<FreeApiResponse> create(Authentication authentication, Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .writer(user.getName())
                .content(freeApiRequest.getContent())
                .status(freeApiRequest.getStatus())
                .isAnonymous(freeApiRequest.getIsAnonymous())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .likes(0L)
                .user(user)
                .build();

        Free newFree = baseRepository.save(free);
        return Header.OK(response(newFree));
    }

    @Override
    @Transactional
//    @Caching(evict = {
//            @CacheEvict(value = "freeReadAll", allEntries = true),
//            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "boardReadAll", allEntries = true),
//            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "home", allEntries = true)
//    })
    public Header<FreeApiResponse> create(Authentication authentication, FileUploadToFreeApiRequest request) {
        FreeApiRequest freeApiRequest = request.getFreeApiRequest();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .writer(user.getName())
                .content(freeApiRequest.getContent())
                .status(freeApiRequest.getStatus())
                .isAnonymous(freeApiRequest.getIsAnonymous())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .likes(0L)
                .user(user)
                .build();

        Free newFree = baseRepository.save(free);

        MultipartFile[] files = request.getFiles();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, newFree.getId(), UploadCategory.POST);

        return Header.OK(response(newFree, fileApiResponseList));
    }

    @Override
    @Transactional
    public Header<FreeApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> baseRepository.save((Free) free))
                .map(this::response)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "freeReadAll", allEntries = true),
//            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "boardReadAll", allEntries = true),
//            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "home", allEntries = true)
//    })
    public Header<FreeApiResponse> update(Authentication authentication, Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }

        Free free = baseRepository.findById(freeApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(freeApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (free.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        free
                .setTitle(freeApiRequest.getTitle())
                .setContent(freeApiRequest.getContent())
                .setStatus(freeApiRequest.getStatus());
        free.setIsAnonymous(freeApiRequest.getIsAnonymous());
        baseRepository.save(free);

        return Header.OK(response(free));
    }

    @Override
    @Transactional
//    @Caching(evict = {
//            @CacheEvict(value = "freeReadAll", allEntries = true),
//            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "boardReadAll", allEntries = true),
//            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "home", allEntries = true)
//    })
    public Header<FreeApiResponse> update(Authentication authentication, FileUploadToFreeApiRequest request) {
        FreeApiRequest freeApiRequest = request.getFreeApiRequest();
        if (freeApiRequest.getStatus().equals(BulletinStatus.REVIEW)) {
            throw new PostStatusNotSuitableException(freeApiRequest.getStatus().getTitle());
        }

        Free free = baseRepository.findById(freeApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(freeApiRequest.getId()));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (free.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        MultipartFile[] files = request.getFiles();
        free.getFileList().stream().forEach(file -> fileRepository.delete(file));
        free.getFileList().clear();
        List<FileApiResponse> fileApiResponseList = fileApiLogicService.uploadFiles(files, free.getId(), UploadCategory.POST);

        free
                .setTitle(freeApiRequest.getTitle())
                .setContent(freeApiRequest.getContent())
                .setStatus(freeApiRequest.getStatus());
        free.setIsAnonymous(freeApiRequest.getIsAnonymous());
        baseRepository.save(free);

        return Header.OK(response(free, fileApiResponseList));
    }

    @Override
//    @Caching(evict = {
//            @CacheEvict(value = "freeReadAll", allEntries = true),
//            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
//            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "boardReadAll", allEntries = true),
//            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
//            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
//            @CacheEvict(value = "home", allEntries = true)
//    })
    public Header delete(Authentication authentication, Long id) {
        Free free = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (free.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        baseRepository.delete(free);
        return Header.OK();
    }

    private FreeApiResponse response(Free free) {
        FreeApiResponse freeApiResponse = FreeApiResponse.builder()
                .id(free.getId())
                .title(free.getTitle())
                .writer(free.getWriter())
                .content(free.getContent())
                .status(free.getStatus())
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .likes(free.getLikes())
                .isAnonymous(free.getIsAnonymous())
                .category(free.getCategory())
                .build();
        if (free.getFileList() == null) {
            free.setFileList(new ArrayList<>());
        } else {
            freeApiResponse.setFileApiResponseList(free.getFileList().stream()
                    .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()));
        }

        return freeApiResponse;
    }

    private FreeApiResponse response(Free free, List<FileApiResponse> fileApiResponseList) {
        FreeApiResponse freeApiResponse = FreeApiResponse.builder()
                .id(free.getId())
                .title(free.getTitle())
                .writer(free.getWriter())
                .content(free.getContent())
                .status(free.getStatus())
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .likes(free.getLikes())
                .isAnonymous(free.getIsAnonymous())
                .category(free.getCategory())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return freeApiResponse;
    }

    @Override
    @Transactional
    public Header<FreeDetailApiResponse> readWithComment(Long id) {
        return baseRepository.findById(id)
                .map(free -> (Free) free.setViews(free.getViews() + 1))
                .map(this::responseWithComment)
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private FreeDetailApiResponse responseWithComment(Free free) {
        FreeDetailApiResponse freeWithCommentApiResponse = FreeDetailApiResponse.builder()
                .id(free.getId())
                .title(free.getTitle())
                .writer(free.getWriter())
                .content(free.getContent())
                .status(free.getStatus())
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .likes(free.getLikes())
                .isAnonymous(free.getIsAnonymous())
                .category(free.getCategory())
                .userId(free.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileApiLogicService.getFileList(free.getFileList(), UploadCategory.POST, free.getId()))
                .commentApiResponseList(commentApiLogicService.searchByPost(free.getId()))
                .build();

        return freeWithCommentApiResponse;
    }

    @Override
    @Transactional
    public Header<FreeDetailApiResponse> readWithCommentAndLike(Authentication authentication, Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return baseRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> baseRepository.save((Free) free))
                .map(free -> responseWithCommentAndLike(principal.getUser(), free))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    private FreeDetailApiResponse responseWithCommentAndLike(User user, Free free) {
        List<CommentApiResponse> commentApiResponseList = commentApiLogicService.searchByPost(free.getId());

        FreeDetailApiResponse freeDetailApiResponse = FreeDetailApiResponse.builder()
                .id(free.getId())
                .title(free.getTitle())
                .writer(free.getWriter())
                .content(free.getContent())
                .status(free.getStatus())
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .likes(free.getLikes())
                .isAnonymous(free.getIsAnonymous())
                .category(free.getCategory())
                .checkLike(false)
                .userId(free.getUser().getId())
                .fileApiResponseList(fileApiLogicService.getFileList(free.getFileList(), UploadCategory.POST, free.getId()))
                .commentApiResponseList(commentApiResponseList)
                .build();

        List<ContentLike> contentLikeList = contentLikeApiLogicService.getContentLikeByUser(user.getId());
        contentLikeList.stream().forEach(contentLike -> {
            if (contentLike.getBoard() != null) {
                if (contentLike.getBoard().getId() == free.getId()) {
                    freeDetailApiResponse.setCheckLike(true);
                }
            } else if (contentLike.getComment() != null) {
                for (CommentApiResponse commentApiResponse : commentApiResponseList) {
                    if (contentLike.getComment().getId() == commentApiResponse.getId()) {
                        commentApiResponse.setCheckLike(true);
                    } else {
                        for (CommentApiResponse subCommentApiResponse : commentApiResponse.getSubComment()) {
                            if (contentLike.getComment().getId() == subCommentApiResponse.getId()) {
                                subCommentApiResponse.setCheckLike(true);
                            }
                        }
                    }
                }
            }
        });
        freeDetailApiResponse.setCommentApiResponseList(commentApiResponseList);

        return freeDetailApiResponse;
    }


    @Override
//    @Cacheable(value = "freeReadAll", key = "#pageable.pageNumber")
    public Header<BoardResponseDTO> readAll(Pageable pageable) {
        Page<Free> frees = baseRepository.findAll(pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
//    @Cacheable(value = "freeSearchByWriter",
//            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByWriterContaining(writer, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
//    @Cacheable(value = "freeSearchByTitle",
//            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByTitleContaining(title, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
//    @Cacheable(value = "freeSearchByTitleOrContent",
//            key = "T(com.aisw.community.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Free> frees = freeRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    public Page<Free> searchByStatus(Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return frees;
    }

    private Header<BoardResponseDTO> getListHeader
            (Page<Free> frees, Page<Free> freesByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(frees.stream()
                        .map(free -> BoardApiResponse.builder()
                                .id(free.getId())
                                .title(free.getTitle())
                                .category(free.getCategory())
                                .createdAt(free.getCreatedAt())
                                .status(free.getStatus())
                                .views(free.getViews())
                                .writer(free.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<BoardApiResponse> boardApiNoticeResponseList = new ArrayList<>();
        List<BoardApiResponse> boardApiUrgentResponseList = new ArrayList<>();
        freesByStatus.stream().forEach(free -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(free.getId())
                    .title(free.getTitle())
                    .category(free.getCategory())
                    .createdAt(free.getCreatedAt())
                    .status(free.getStatus())
                    .views(free.getViews())
                    .writer(free.getWriter())
                    .build();
            if (boardApiResponse.getStatus() == BulletinStatus.NOTICE) {
                boardApiNoticeResponseList.add(boardApiResponse);
            } else if (boardApiResponse.getStatus() == BulletinStatus.URGENT) {
                boardApiUrgentResponseList.add(boardApiResponse);
            }
        });
        boardResponseDTO.setBoardApiNoticeResponseList(boardApiNoticeResponseList);
        boardResponseDTO.setBoardApiUrgentResponseList(boardApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(frees.getTotalElements())
                .totalPages(frees.getTotalPages())
                .currentElements(frees.getNumberOfElements())
                .currentPage(frees.getNumber())
                .build();

        return Header.OK(boardResponseDTO, pagination);
    }
}
