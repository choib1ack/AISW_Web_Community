package com.aisw.community.service;

import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.QnaCommentApiRequest;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import com.aisw.community.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaCommentApiLogicService extends BaseService<QnaCommentApiRequest, QnaCommentApiResponse, QnaComment> {

    @Autowired
    private QnaRepository qnaRepository;

    @Override
    public Header<QnaCommentApiResponse> create(Header<QnaCommentApiRequest> request) {
        QnaCommentApiRequest qnaCommentApiRequest = request.getData();

        QnaComment qnaComment = QnaComment.builder()
                .comment(qnaCommentApiRequest.getComment())
                .likes(qnaCommentApiRequest.getLikes())
                .isAnonymous(qnaCommentApiRequest.getIsAnonymous())
                .qna(qnaRepository.getOne(qnaCommentApiRequest.getQnaId()))
                .build();

        QnaComment newQnaComment = baseRepository.save(qnaComment);
        return Header.OK(response(newQnaComment));
    }

    @Override
    public Header<QnaCommentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<QnaCommentApiResponse> update(Header<QnaCommentApiRequest> request) {
        QnaCommentApiRequest qnaCommentApiRequest = request.getData();

        return baseRepository.findById(qnaCommentApiRequest.getId())
                .map(qnaComment -> {
                    qnaComment
                            .setComment(qnaCommentApiRequest.getComment())
                            .setLikes(qnaCommentApiRequest.getLikes())
                            .setIsAnonymous(qnaCommentApiRequest.getIsAnonymous())
                            .setQna(qnaRepository.getOne(qnaCommentApiRequest.getQnaId()));

                    return qnaComment;
                })
                .map(qnaComment -> baseRepository.save(qnaComment))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(qnaComment -> {
                    baseRepository.delete(qnaComment);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private QnaCommentApiResponse response(QnaComment qnaComment) {
        QnaCommentApiResponse qnaCommentApiResponse = QnaCommentApiResponse.builder()
                .id(qnaComment.getId())
                .comment(qnaComment.getComment())
                .createdAt(qnaComment.getCreatedAt())
                .createdBy(qnaComment.getCreatedBy())
                .likes(qnaComment.getLikes())
                .isAnonymous(qnaComment.getIsAnonymous())
                .qnaId(qnaComment.getQna().getId())
                .build();

        return qnaCommentApiResponse;
    }

    @Override
    public Header<List<QnaCommentApiResponse>> search(Pageable pageable) {
        Page<QnaComment> qnaComments = baseRepository.findAll(pageable);

        List<QnaCommentApiResponse> qnaCommentApiResponseList = qnaComments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(qnaCommentApiResponseList);
    }
}
