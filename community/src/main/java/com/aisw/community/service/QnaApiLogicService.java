package com.aisw.community.service;

import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.Account;
import com.aisw.community.model.entity.ContentLike;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.*;
import com.aisw.community.repository.AccountRepository;
import com.aisw.community.repository.ContentLikeRepository;
import com.aisw.community.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaApiLogicService extends BoardPostService<QnaApiRequest, BoardResponseDTO, QnaDetailApiResponse, QnaApiResponse, Qna> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @Override
    public Header<QnaApiResponse> create(Header<QnaApiRequest> request) {
        //
        QnaApiRequest qnaApiRequest = request.getData();
        Account account = accountRepository.findById(qnaApiRequest.getAccountId()).orElseThrow(UserNotFoundException::new);
        Qna qna = Qna.builder()
                .title(qnaApiRequest.getTitle())
                .writer(account.getName())
                .content(qnaApiRequest.getContent())
                .status(qnaApiRequest.getStatus())
                .views(0L)
                .likes(0L)
                .subject(qnaApiRequest.getSubject())
                .isAnonymous(qnaApiRequest.getIsAnonymous())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.QNA)
                .account(account)
                .build();

        Qna newQna = baseRepository.save(qna);
        return Header.OK(response(newQna));
    }

    @Override
    public Header<QnaApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(qna -> qna.setViews(qna.getViews() + 1))
                .map(qna -> baseRepository.save((Qna) qna))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    @Transactional
    public Header<QnaApiResponse> update(Header<QnaApiRequest> request) {
        QnaApiRequest qnaApiRequest = request.getData();

        return baseRepository.findById(qnaApiRequest.getId())
                .map(qna -> {
                    qna
                            .setTitle(qnaApiRequest.getTitle())
                            .setContent(qnaApiRequest.getContent())
                            .setStatus(qnaApiRequest.getStatus());
                    qna.setIsAnonymous(qnaApiRequest.getIsAnonymous());
                    qna.setSubject(qnaApiRequest.getSubject());

                    return qna;
                })
                .map(qna -> baseRepository.save(qna))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(qna -> {
                    baseRepository.delete(qna);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
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
                .isAnonymous(qna.getIsAnonymous())
                .subject(qna.getSubject())
                .accountId(qna.getAccount().getId())
                .category(qna.getCategory())
                .build();

        return qnaApiResponse;
    }

    @Override
    @Transactional
    public Header<QnaDetailApiResponse> readWithComment(Long id) {
        return baseRepository.findById(id)
                .map(qna -> qna.setViews(qna.getViews() + 1))
                .map(qna -> baseRepository.save((Qna) qna))
                .map(this::responseWithComment)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private QnaDetailApiResponse responseWithComment(Qna qna) {
        QnaDetailApiResponse qnaWithCommentApiResponse = QnaDetailApiResponse.builder()
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
                .isAnonymous(qna.getIsAnonymous())
                .subject(qna.getSubject())
                .category(qna.getCategory())
                .accountId(qna.getAccount().getId())
                .build();

        return qnaWithCommentApiResponse;
    }

    @Override
    @Transactional
    public Header<QnaDetailApiResponse> readWithCommentAndLike(Long postId, Long accountId) {
        return baseRepository.findById(postId)
                .map(qna -> qna.setViews(qna.getViews() + 1))
                .map(qna -> baseRepository.save((Qna) qna))
                .map(qna -> responseWithCommentAndLike(qna, accountId))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private QnaDetailApiResponse responseWithCommentAndLike(Qna qna, Long accountId) {
        List<ContentLike> contentLikeList = contentLikeRepository.findAllByAccountId(accountId);
        List<CommentApiResponse> commentApiResponseList = commentApiLogicService.searchByPost(qna.getId());

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
                .isAnonymous(qna.getIsAnonymous())
                .category(qna.getCategory())
                .accountId(qna.getAccount().getId())
                .build();
        contentLikeList.stream().forEach(contentLike -> {
            if (contentLike.getBoard() != null && contentLike.getBoard().getId() == qna.getId()) {
                qnaDetailApiResponse.setCheckLike(true);
            }
            for (int i = 0; i < commentApiResponseList.size(); i++) {
                if (contentLike.getComment() != null &&
                        contentLike.getComment().getId() == commentApiResponseList.get(i).getId()) {
                    commentApiResponseList.get(i).setCheckLike(true);
                }
            }
            qnaDetailApiResponse.setCommentApiResponseList(commentApiResponseList);
        });

        return qnaDetailApiResponse;
    }

    @Override
    public Header<BoardResponseDTO> search(Pageable pageable) {
        Page<Qna> qnas = baseRepository.findAll(pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByWriterContaining(writer, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByTitleContaining(title, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Qna> qnas = qnaRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

//    @Cacheable(value = "searchBySubject", key = "#subject")
    public Header<BoardResponseDTO> searchBySubject(List<String> subject, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllBySubjectIn(subject, pageable);
        Page<Qna> qnasByStatus = searchByStatus(pageable);

        return getListHeader(qnas, qnasByStatus);
    }

    public Page<Qna> searchByStatus(Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return qnas;
    }

    private Header<BoardResponseDTO> getListHeader(Page<Qna> qnas, Page<Qna> qnasByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(qnas.stream()
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
        qnasByStatus.stream().forEach(board -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .createdAt(board.getCreatedAt())
                    .status(board.getStatus())
                    .views(board.getViews())
                    .writer(board.getWriter())
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
