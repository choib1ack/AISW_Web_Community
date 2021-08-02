package com.aisw.community.service.post.board;

import com.aisw.community.advice.exception.NotEqualAccountException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.post.board.Free;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.FileUploadToFreeDTO;
import com.aisw.community.model.network.request.post.board.FreeApiRequest;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.FreeApiResponse;
import com.aisw.community.model.network.response.post.board.FreeDetailApiResponse;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.FreeRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.service.post.comment.CommentApiLogicService;
import com.aisw.community.service.post.file.FileApiLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeApiLogicService extends BoardPostService<FreeApiRequest, FileUploadToFreeDTO, BoardResponseDTO, FreeDetailApiResponse, FreeApiResponse, Free> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @Autowired
    private FileApiLogicService fileApiLogicService;

    @Override
    public Header<FreeApiResponse> create(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();
        User user = userRepository.findById(freeApiRequest.getUserId()).orElseThrow(
                () -> new UserNotFoundException(freeApiRequest.getUserId()));
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
    public Header<FreeApiResponse> create(FileUploadToFreeDTO request) {
        FreeApiRequest freeApiRequest = request.getFreeApiRequest();

        User user = userRepository.findById(freeApiRequest.getUserId()).orElseThrow(
                () -> new UserNotFoundException(freeApiRequest.getUserId()));
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
    @Transactional
    public Header<FreeApiResponse> update(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        Free free = baseRepository.findById(freeApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(freeApiRequest.getId()));

        if (free.getUser().getId() != freeApiRequest.getUserId()) {
            throw new NotEqualAccountException(freeApiRequest.getUserId());
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
    public Header<FreeApiResponse> update(FileUploadToFreeDTO request) {
        FreeApiRequest freeApiRequest = request.getFreeApiRequest();
        MultipartFile[] files = request.getFiles();

        Free free = baseRepository.findById(freeApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(freeApiRequest.getId()));

        if (free.getUser().getId() != freeApiRequest.getUserId()) {
            throw new NotEqualAccountException(freeApiRequest.getUserId());
        }

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
    public Header delete(Long id, Long userId) {
        Free free = baseRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        if (free.getUser().getId() != userId) {
            throw new NotEqualAccountException(userId);
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
                .accountId(free.getUser().getId())
                .fileApiResponseList(free.getFileList().stream()
                        .map(file -> fileApiLogicService.response(file)).collect(Collectors.toList()))
                .build();

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
                .accountId(free.getUser().getId())
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
                .accountId(free.getUser().getId())
                .checkLike(false)
                .commentApiResponseList(commentApiLogicService.searchByPost(free.getId()))
                .build();

        return freeWithCommentApiResponse;
    }

    @Override
    @Transactional
    public Header<FreeDetailApiResponse> readWithCommentAndLike(Long postId, Long accountId) {
        return baseRepository.findById(postId)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> baseRepository.save((Free) free))
                .map(free -> responseWithCommentAndLike(free, accountId))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private FreeDetailApiResponse responseWithCommentAndLike(Free free, Long accountId) {
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
                .accountId(free.getUser().getId())
                .build();

        List<ContentLike> contentLikeList = contentLikeRepository.findAllByUserId(accountId);
        contentLikeList.stream().forEach(contentLike -> {
            if (contentLike.getBoard() != null) {
                if (contentLike.getBoard().getId() == free.getId()) {
                    freeDetailApiResponse.setCheckLike(true);
                }
            } else if (contentLike.getComment() != null) {
                for (CommentApiResponse commentApiResponse : commentApiResponseList) {
                    if (contentLike.getComment().getId() == commentApiResponse.getId()) {
                        commentApiResponse.setCheckLike(true);
                    }
                    else {
                        for(CommentApiResponse subCommentApiResponse : commentApiResponse.getSubComment()) {
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
    public Header<BoardResponseDTO> search(Pageable pageable) {
        Page<Free> frees = baseRepository.findAll(pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByWriterContaining(writer, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByTitleContaining(title, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
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
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
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
