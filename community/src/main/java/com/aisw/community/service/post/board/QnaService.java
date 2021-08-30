package com.aisw.community.service.post.board;

import com.aisw.community.component.advice.exception.NotEqualUserException;
import com.aisw.community.component.advice.exception.PostNotFoundException;
import com.aisw.community.component.advice.exception.PostStatusNotSuitableException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.board.QJob;
import com.aisw.community.model.entity.post.board.Qna;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.enumclass.UploadCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.post.board.FileUploadToQnaRequest;
import com.aisw.community.model.network.request.post.board.QnaApiRequest;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.model.network.response.post.board.QnaApiResponse;
import com.aisw.community.model.network.response.post.board.QnaDetailApiResponse;
import com.aisw.community.model.network.response.post.comment.CommentApiResponse;
import com.aisw.community.model.network.response.post.file.FileApiResponse;
import com.aisw.community.repository.post.board.QnaRepository;
import com.aisw.community.repository.post.file.FileRepository;
import com.aisw.community.service.post.comment.CommentService;
import com.aisw.community.service.post.file.FileService;
import com.aisw.community.service.post.like.ContentLikeService;
import com.aisw.community.service.user.UserService;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaService implements BoardPostService<QnaApiRequest, QnaApiResponse, BoardResponseDTO, QnaDetailApiResponse> {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private ContentLikeService contentLikeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;

    @Override
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<QnaApiResponse> create(User user, QnaApiRequest qnaApiRequest) {
        Qna qna = Qna.builder()
                .title(qnaApiRequest.getTitle())
                .writer((qnaApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .content(qnaApiRequest.getContent())
                .status(qnaApiRequest.getStatus())
                .subject(qnaApiRequest.getSubject())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.QNA)
                .likes(0L)
                .user(user)
                .build();

        Qna newQna = qnaRepository.save(qna);
        return Header.OK(response(newQna));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<QnaApiResponse> create(User user, QnaApiRequest qnaApiRequest, MultipartFile[] files) {
        Qna qna = Qna.builder()
                .title(qnaApiRequest.getTitle())
                .writer((qnaApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .content(qnaApiRequest.getContent())
                .status(qnaApiRequest.getStatus())
                .subject(qnaApiRequest.getSubject())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.QNA)
                .likes(0L)
                .user(user)
                .build();
        Qna newQna = qnaRepository.save(qna);

        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, "/auth-student/board/qna", newQna.getId(), UploadCategory.POST);

            return Header.OK(response(newQna, fileApiResponseList));
        } else {
            return Header.OK(response(newQna));
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<QnaDetailApiResponse> read(Long id) {
        return qnaRepository.findById(id)
                .map(qna -> qna.setViews(qna.getViews() + 1))
                .map(qna -> qnaRepository.save((Qna) qna))
                .map(qna -> responseWithComment(qna))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true)
    })
    public Header<QnaDetailApiResponse> read(User user, Long id) {
        return qnaRepository.findById(id)
                .map(qna -> qna.setViews(qna.getViews() + 1))
                .map(qna -> qnaRepository.save((Qna) qna))
                .map(qna -> (user == null) ? responseWithComment(qna) : responseWithCommentAndLike(user, qna))
                .map(Header::OK)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<QnaApiResponse> update(User user, QnaApiRequest qnaApiRequest) {
        Qna qna = qnaRepository.findById(qnaApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(qnaApiRequest.getId()));
        if(qna.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        qna
                .setWriter((qnaApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .setTitle(qnaApiRequest.getTitle())
                .setContent(qnaApiRequest.getContent())
                .setStatus(qnaApiRequest.getStatus());
        qna.setSubject(qnaApiRequest.getSubject());
        qnaRepository.save(qna);

        return Header.OK(response(qna));
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "boardReadAll", allEntries = true),
            @CacheEvict(value = "boardSearchByWriter", allEntries = true),
            @CacheEvict(value = "boardSearchByTitle", allEntries = true),
            @CacheEvict(value = "boardSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "bulletinSearchByWriter", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitle", allEntries = true),
            @CacheEvict(value = "bulletinSearchByTitleOrContent", allEntries = true),
            @CacheEvict(value = "home", allEntries = true)
    })
    public Header<QnaApiResponse> update(User user, QnaApiRequest qnaApiRequest, MultipartFile[] files) {
        Qna qna = qnaRepository.findById(qnaApiRequest.getId()).orElseThrow(
                () -> new PostNotFoundException(qnaApiRequest.getId()));
        if(qna.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        qna
                .setWriter((qnaApiRequest.getIsAnonymous() == true) ? "익명" : user.getName())
                .setTitle(qnaApiRequest.getTitle())
                .setContent(qnaApiRequest.getContent())
                .setStatus(qnaApiRequest.getStatus());
        qna.setSubject(qnaApiRequest.getSubject());
        qnaRepository.save(qna);

        if(qna.getFileList() != null) {
            fileService.deleteFileList(qna.getFileList());
        }
        if(files != null) {
            List<FileApiResponse> fileApiResponseList =
                    fileService.uploadFiles(files, "/auth-student/board/qna", qna.getId(), UploadCategory.POST);

            return Header.OK(response(qna, fileApiResponseList));
        } else {
            return Header.OK(response(qna));
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "qnaReadAll", allEntries = true),
            @CacheEvict(value = "qnaSearchByWriter", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitle", allEntries = true),
            @CacheEvict(value = "qnaSearchByTitleOrContent", allEntries = true),
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
        Qna qna = qnaRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        if (qna.getUser().getId() != user.getId()) {
            throw new NotEqualUserException(user.getId());
        }

        fileService.deleteFileList(qna.getFileList());
        qnaRepository.delete(qna);
        return Header.OK();
    }

    private QnaApiResponse response(Qna qna) {
        QnaApiResponse qnaApiResponse = QnaApiResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .writer(qna.getWriter())
                .content(qna.getContent())
                .status(qna.getStatus())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .likes(qna.getLikes())
                .subject(qna.getSubject())
                .category(qna.getCategory())
                .build();
        if (qna.getFileList() != null) {
            qnaApiResponse.setFileApiResponseList(fileService.getFileList(qna.getFileList(), UploadCategory.POST, qna.getId()));
        }

        return qnaApiResponse;
    }

    private QnaApiResponse response(Qna qna, List<FileApiResponse> fileApiResponseList) {
        QnaApiResponse qnaApiResponse = QnaApiResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .writer(qna.getWriter())
                .content(qna.getContent())
                .status(qna.getStatus())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .likes(qna.getLikes())
                .subject(qna.getSubject())
                .category(qna.getCategory())
                .fileApiResponseList(fileApiResponseList)
                .build();

        return qnaApiResponse;
    }

    private QnaDetailApiResponse responseWithComment(Qna qna) {
        return QnaDetailApiResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .writer(qna.getWriter())
                .content(qna.getContent())
                .status(qna.getStatus())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .likes(qna.getLikes())
                .subject(qna.getSubject())
                .category(qna.getCategory())
                .userId(qna.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileService.getFileList(qna.getFileList(), UploadCategory.POST, qna.getId()))
                .commentApiResponseList(commentService.searchByPost(qna.getId()))
                .build();
    }

    private QnaDetailApiResponse responseWithCommentAndLike(User user, Qna qna) {
        QnaDetailApiResponse qnaDetailApiResponse = QnaDetailApiResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .writer(qna.getWriter())
                .content(qna.getContent())
                .status(qna.getStatus())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .likes(qna.getLikes())
                .subject(qna.getSubject())
                .category(qna.getCategory())
                .userId(qna.getUser().getId())
                .checkLike(false)
                .fileApiResponseList(fileService.getFileList(qna.getFileList(), UploadCategory.POST, qna.getId()))
                .build();

        List<CommentApiResponse> commentApiResponseList = commentService.searchByPost(user, qna.getId());
        List<ContentLike> contentLikeList = contentLikeService.getContentLikeByUser(user.getId());
        contentLikeList.stream().forEach(contentLike -> {
            if (contentLike.getBoard() != null) {
                if (contentLike.getBoard().getId() == qna.getId()) {
                    qnaDetailApiResponse.setCheckLike(true);
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
        qnaDetailApiResponse.setCommentApiResponseList(commentApiResponseList);
        qnaDetailApiResponse.setIsWriter((user.getId() == qna.getUser().getId()) ? true : false);

        return qnaDetailApiResponse;
    }

    @Override
    @Cacheable(value = "qnaReadAll", key = "#pageable.pageNumber")
    public Header<BoardResponseDTO> readAll(Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAll(pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    @Cacheable(value = "qnaSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByWriterContaining(writer, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    @Cacheable(value = "qnaSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByTitleContaining(title, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    @Cacheable(value = "qnaSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Qna> qnas = qnaRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    public Header<BoardResponseDTO> searchBySubject(List<String> subject, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllBySubjectIn(subject, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    public Page<Qna> searchByStatus(Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByStatusIn(
                Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE), pageable);

        return qnas;
    }

    private Header<BoardResponseDTO> getListHeader(Page<Qna> qnas, Page<Qna> qnasByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(qnas.stream()
                        .map(qna -> BoardApiResponse.builder()
                                .id(qna.getId())
                                .title(qna.getTitle())
                                .category(qna.getCategory())
                                .createdAt(qna.getCreatedAt())
                                .status(qna.getStatus())
                                .views(qna.getViews())
                                .writer(qna.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<BoardApiResponse> boardApiNoticeResponseList = new ArrayList<>();
        List<BoardApiResponse> boardApiUrgentResponseList = new ArrayList<>();
        qnasByStatus.stream().forEach(qna -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(qna.getId())
                    .title(qna.getTitle())
                    .category(qna.getCategory())
                    .createdAt(qna.getCreatedAt())
                    .status(qna.getStatus())
                    .views(qna.getViews())
                    .writer(qna.getWriter())
                    .build();
            if(boardApiResponse.getStatus() == BulletinStatus.NOTICE) {
                boardApiNoticeResponseList.add(boardApiResponse);
            }
            else if(boardApiResponse.getStatus() == BulletinStatus.URGENT) {
                boardApiUrgentResponseList.add(boardApiResponse);
            }
        });
        boardResponseDTO.setBoardApiNoticeResponseList(boardApiNoticeResponseList);
        boardResponseDTO.setBoardApiUrgentResponseList(boardApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(qnas.getTotalElements())
                .totalPages(qnas.getTotalPages())
                .currentElements(qnas.getNumberOfElements())
                .currentPage(qnas.getNumber())
                .build();

        return Header.OK(boardResponseDTO, pagination);
    }
}
