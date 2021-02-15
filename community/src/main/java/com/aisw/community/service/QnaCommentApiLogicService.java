package com.aisw.community.service;

import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import com.aisw.community.repository.QnaCommentRepository;
import com.aisw.community.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaCommentApiLogicService {

    @Autowired
    private QnaRepository qnaRepository;

    @Autowired
    private QnaCommentRepository qnaCommentRepository;

    public Header<QnaCommentApiResponse> create(Header<QnaCommentApiRequest> request) {
        QnaCommentApiRequest qnaCommentApiRequest = request.getData();

        QnaComment qnaComment = QnaComment.builder()
                .comment(qnaCommentApiRequest.getComment())
                .likes(qnaCommentApiRequest.getLikes())
                .isAnonymous(qnaCommentApiRequest.getIsAnonymous())
                .qna(qnaRepository.getOne(qnaCommentApiRequest.getQnaId()))
                .build();

        QnaComment newQnaComment = qnaCommentRepository.save(qnaComment);
        return Header.OK(response(newQnaComment));
    }

    public Header delete(Long id) {
        return qnaCommentRepository.findById(id)
                .map(qnaComment -> {
                    qnaCommentRepository.delete(qnaComment);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private QnaCommentApiResponse response(QnaComment qnaComment) {
        QnaCommentApiResponse freeCommentApiResponse = QnaCommentApiResponse.builder()
                .id(qnaComment.getId())
                .comment(qnaComment.getComment())
                .createdAt(qnaComment.getCreatedAt())
                .createdBy(qnaComment.getCreatedBy())
                .likes(qnaComment.getLikes())
                .isAnonymous(qnaComment.getIsAnonymous())
                .qnaId(qnaComment.getQna().getId())
                .build();

        return freeCommentApiResponse;
    }

    public Header<List<QnaCommentApiResponse>> searchByPost(Long id, Pageable pageable) {
        Page<QnaComment> qnaComments = qnaCommentRepository.findAllByQnaId(id, pageable);

        List<QnaCommentApiResponse> qnaCommentApiResponseList = qnaComments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(qnaComments.getTotalElements())
                .totalPages(qnaComments.getTotalPages())
                .currentElements(qnaComments.getNumberOfElements())
                .currentPage(qnaComments.getNumber())
                .build();

        return Header.OK(qnaCommentApiResponseList, pagination);
    }
}
