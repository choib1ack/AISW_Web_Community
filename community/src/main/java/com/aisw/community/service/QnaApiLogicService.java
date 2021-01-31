package com.aisw.community.service;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.Notice;
import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.BoardApiRequest;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.request.NoticeApiRequest;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.model.network.response.NoticeApiResponse;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.FreeRepository;
import com.aisw.community.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaApiLogicService extends BaseService<QnaApiRequest, QnaApiResponse, Qna> {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @Override
    public Header<QnaApiResponse> create(Header<QnaApiRequest> request) {
        QnaApiRequest qnaApiRequest = request.getData();

        BoardApiRequest boardApiRequest = BoardApiRequest.builder()
                .userId(request.getData().getUserId())
                .build();
        BoardApiResponse boardApiResponse = boardApiLogicService.create(Header.OK(boardApiRequest)).getData();

        Qna qna = Qna.builder()
                .title(qnaApiRequest.getTitle())
                .content(qnaApiRequest.getContent())
                .attachmentFile(qnaApiRequest.getAttachmentFile())
                .views(qnaApiRequest.getViews())
                .likes(qnaApiRequest.getLikes())
                .subject(qnaApiRequest.getSubject())
                .isAnonymous(qnaApiRequest.getIsAnonymous())
                .level(qnaApiRequest.getLevel())
                .board(boardRepository.getOne(boardApiResponse.getId()))
                .build();

        Qna newQna = baseRepository.save(qna);
        return Header.OK(response(newQna));
    }

    @Override
    public Header<QnaApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<QnaApiResponse> update(Header<QnaApiRequest> request) {
        QnaApiRequest qnaApiRequest = request.getData();

        return baseRepository.findById(qnaApiRequest.getId())
                .map(qna -> {
                    qna
                            .setTitle(qnaApiRequest.getTitle())
                            .setContent(qnaApiRequest.getContent())
                            .setAttachmentFile(qnaApiRequest.getAttachmentFile())
                            .setViews(qnaApiRequest.getViews())
                            .setLevel(qnaApiRequest.getLevel())
                            .setLikes(qnaApiRequest.getLikes())
                            .setIsAnonymous(qnaApiRequest.getIsAnonymous())
                            .setSubject(qnaApiRequest.getSubject());

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
                    boardRepository.findById(qna.getBoard().getId())
                            .map(board -> {
                                boardRepository.delete(board);
                                return Header.OK();
                            })
                            .orElseGet(() -> Header.ERROR("데이터 없음"));
                    baseRepository.delete(qna);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private QnaApiResponse response(Qna qna) {
        QnaApiResponse qnaApiResponse = QnaApiResponse.builder()
                .id(qna.getId())
                .title(qna.getTitle())
                .content(qna.getContent())
                .attachmentFile(qna.getAttachmentFile())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .level(qna.getLevel())
                .likes(qna.getLikes())
                .isAnonymous(qna.getIsAnonymous())
                .subject(qna.getSubject())
                .boardId(qna.getBoard().getId())
                .build();

        return qnaApiResponse;
    }

    @Override
    public Header<List<QnaApiResponse>> search(Pageable pageable) {
        Page<Qna> qnas = baseRepository.findAll(pageable);

        List<QnaApiResponse> qnaApiResponseList = qnas.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(qnaApiResponseList);
    }
}
