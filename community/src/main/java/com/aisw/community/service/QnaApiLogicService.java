package com.aisw.community.service;

import com.aisw.community.model.entity.Qna;
import com.aisw.community.model.enumclass.BoardCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.QnaApiRequest;
import com.aisw.community.model.network.response.QnaApiResponse;
import com.aisw.community.repository.QnaRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QnaApiLogicService extends PostService<QnaApiRequest, QnaApiResponse, Qna> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QnaRepository qnaRepository;

    @Override
    public Header<QnaApiResponse> create(Header<QnaApiRequest> request) {
        QnaApiRequest qnaApiRequest = request.getData();

        Qna qna = Qna.builder()
                .title(qnaApiRequest.getTitle())
                .writer(userRepository.getOne(qnaApiRequest.getUserId()).getName())
                .content(qnaApiRequest.getContent())
                .attachmentFile(qnaApiRequest.getAttachmentFile())
                .status(qnaApiRequest.getStatus())
                .views(0L)
                .likes(0L)
                .subject(qnaApiRequest.getSubject())
                .isAnonymous(qnaApiRequest.getIsAnonymous())
                .level(qnaApiRequest.getLevel())
                .category(BoardCategory.QNA)
                .user(userRepository.getOne(qnaApiRequest.getUserId()))
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
                            .setAttachmentFile(qnaApiRequest.getAttachmentFile())
                            .setStatus(qnaApiRequest.getStatus())
                            .setLevel(qnaApiRequest.getLevel())
                            .setIsAnonymous(qnaApiRequest.getIsAnonymous());
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
                .attachmentFile(qna.getAttachmentFile())
                .status(qna.getStatus())
                .createdAt(qna.getCreatedAt())
                .createdBy(qna.getCreatedBy())
                .updatedAt(qna.getUpdatedAt())
                .updatedBy(qna.getUpdatedBy())
                .views(qna.getViews())
                .level(qna.getLevel())
                .likes(qna.getLikes())
                .isAnonymous(qna.getIsAnonymous())
                .subject(qna.getSubject())

                .userId(qna.getUser().getId())
                .build();

        return qnaApiResponse;
    }

    @Override
    public Header<List<QnaApiResponse>> search(Pageable pageable) {
        Page<Qna> qnas = baseRepository.findAll(pageable);

        return getListHeader(qnas);
    }

    @Override
    public Header<List<QnaApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(qnas);
    }

    @Override
    public Header<List<QnaApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Qna> qnas = qnaRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(qnas);
    }

    @Override
    public Header<List<QnaApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Qna> qnas = qnaRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(qnas);
    }

    private Header<List<QnaApiResponse>> getListHeader(Page<Qna> qnas) {
        List<QnaApiResponse> qnaApiResponseList = qnas.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(qnas.getTotalElements())
                .totalPages(qnas.getTotalPages())
                .currentElements(qnas.getNumberOfElements())
                .currentPage(qnas.getNumber())
                .build();

        return Header.OK(qnaApiResponseList, pagination);
    }

    @Transactional
    public Header<QnaApiResponse> pressLikes(Long id) {
        return baseRepository.findById(id)
                .map(qna -> qna.setLikes(qna.getLikes() + 1))
                .map(qna -> baseRepository.save((Qna)qna))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }
}
