package com.aisw.community.service.post.board;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.FreeRepository;
import com.aisw.community.service.post.comment.CommentService;
import com.aisw.community.service.post.file.FileService;
import com.aisw.community.service.post.like.ContentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeService implements BoardPostService<FreeApiRequest, FreeApiResponse, BoardResponseDTO, FreeDetailApiResponse> {

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ContentLikeService contentLikeService;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<FreeApiResponse> create(User user, FreeApiRequest freeApiRequest, MultipartFile[] files) {
        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .writer((freeApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .content(freeApiRequest.getContent())
                .status(freeApiRequest.getStatus())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .likes(0L)
                .user(user)
                .build();
        Free newFree = freeRepository.save(free);

        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/board/free", newFree.getId(), UploadCategory.POST);

            return Header.OK(response(newFree, fileApiResponseList));
        } else {
            return Header.OK(response(newFree));
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<FreeDetailApiResponse> read(Long id) {
        return freeRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> freeRepository.save((Free) free))
                .map(free -> responseWithComment(free))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<FreeDetailApiResponse> read(User user, Long id) {
        return freeRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> freeRepository.save((Free) free))
                .map(free -> responseWithCommentAndLike(user, free))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<FreeApiResponse> update(User user, FreeApiRequest freeApiRequest, MultipartFile[] files, List<Long> delFileIdList) {
        Free free = freeRepository.findById(freeApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(freeApiRequest.getId()));

        if (free.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        free
                .setWriter((freeApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .setTitle(freeApiRequest.getTitle())
                .setContent(freeApiRequest.getContent())
                .setStatus(freeApiRequest.getStatus());
        freeRepository.save(free);

        if(free.getFileList() != null && delFileIdList != null) {
            List<File> delFileList = new ArrayList<>();
            for(File file : free.getFileList()) {
                if(delFileIdList.contains(file.getId())) {
                    fileService.deleteFile(file);
                    delFileList.add(file);
                }
            }
            for (File file : delFileList) {
                free.getFileList().remove(file);
            }
        }
        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, user.getUsername(), "/board/free", free.getId(), UploadCategory.POST);
            return Header.OK(response(free, fileApiResponseList));
        } else {
            return Header.OK(response(free));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "commentSearchByPost", key = "#id"),
            @CacheEvict(value = "freeReadAll", allEntries = true),
            @CacheEvict(value = "freeSearchByWriter", allEntries = true),
            @CacheEvict(value = "freeSearchByTitle", allEntries = true),
            @CacheEvict(value = "freeSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header delete(User user, Long id) {
        Free free = freeRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (free.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        fileService.deleteFileList(free.getFileList());
        freeRepository.delete(free);
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
                .category(free.getCategory())
                .build();
        if (free.getFileList() != null) {
            freeApiResponse.setFileApiResponseList(fileService.getFileList(free.getFileList()));
        }

        return freeApiResponse;
    }

    private FreeApiResponse response(Free free, List<FileApiResponse> fileApiResponseList) {
        if(free.getFileList() != null) {
            fileApiResponseList.addAll(fileService.getFileList(free.getFileList()));
        }
        return FreeApiResponse.builder()
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
                .category(free.getCategory())
                .fileApiResponseList(fileApiResponseList)
                .build();
    }

    private FreeDetailApiResponse responseWithComment(Free free) {
        return FreeDetailApiResponse.builder()
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
                .category(free.getCategory())
                .userId(free.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileService.getFileList(free.getFileList()))
                .commentApiResponseList(commentService.searchByPost(free.getId()))
                .build();
    }

    private FreeDetailApiResponse responseWithCommentAndLike(User user, Free free) {
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
                .category(free.getCategory())
                .checkLike(false)
                .userId(free.getUser().getId())
                .fileApiResponseList(fileService.getFileList(free.getFileList()))
                .build();

        List<CommentApiResponse> commentApiResponseList = commentService.searchByPost(user, free.getId());
        List<ContentLike> contentLikeList = contentLikeService.getContentLikeByUser(user.getId());
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
        freeDetailApiResponse.setIsWriter((user.getId() == free.getUser().getId()) ? true : false);

        return freeDetailApiResponse;
    }


    @Override
    @Cacheable(value = "freeReadAll", key = "#pageable.pageNumber")
    public Header<BoardResponseDTO> readAll(Pageable pageable) {
        Page<Free> frees = freeRepository.findAll(pageable);
        List<Free> freesByStatus = searchByStatus();

        return response(frees, freesByStatus);
    }

    @Override
    @Cacheable(value = "freeSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByWriterContaining(writer, pageable);
        List<Free> freesByStatus = searchByStatus();

        return response(frees, freesByStatus);
    }

    @Override
    @Cacheable(value = "freeSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByTitleContaining(title, pageable);
        List<Free> freesByStatus = searchByStatus();

        return response(frees, freesByStatus);
    }

    @Override
    @Cacheable(value = "freeSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Free> frees = freeRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        List<Free> freesByStatus = searchByStatus();

        return response(frees, freesByStatus);
    }

    public List<Free> searchByStatus() {
        return freeRepository.findTop10ByStatusIn(Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE));
    }

    private Header<BoardResponseDTO> response(Page<Free> frees, List<Free> freesByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(frees.stream()
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
                                .hasFile((board.getFileList().size() != 0) ? true : false)
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<BoardApiResponse> boardApiNoticeResponseList = new ArrayList<>();
        List<BoardApiResponse> boardApiUrgentResponseList = new ArrayList<>();
        freesByStatus.stream().forEach(board -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .createdAt(board.getCreatedAt())
                    .status(board.getStatus())
                    .views(board.getViews())
                    .writer(board.getWriter())
                    .hasFile((board.getFileList().size() != 0) ? true : false)
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
