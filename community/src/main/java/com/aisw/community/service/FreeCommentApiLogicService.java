package com.aisw.community.service;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.entity.QnaComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.model.network.response.QnaCommentApiResponse;
import com.aisw.community.repository.FreeCommentRepository;
import com.aisw.community.repository.FreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeCommentApiLogicService {

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private FreeCommentRepository freeCommentRepository;

    public Header<FreeCommentApiResponse> create(Header<FreeCommentApiRequest> request) {
        FreeCommentApiRequest freeCommentApiRequest = request.getData();

        FreeComment freeComment = FreeComment.builder()
                .comment(freeCommentApiRequest.getComment())
                .likes(freeCommentApiRequest.getLikes())
                .isAnonymous(freeCommentApiRequest.getIsAnonymous())
                .free(freeRepository.getOne(freeCommentApiRequest.getFreeId()))
                .build();

        FreeComment newFreeComment = freeCommentRepository.save(freeComment);
        return Header.OK(response(newFreeComment));
    }

    public Header delete(Long id) {
        return freeCommentRepository.findById(id)
                .map(freeComment -> {
                    freeCommentRepository.delete(freeComment);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private FreeCommentApiResponse response(FreeComment freeComment) {
        FreeCommentApiResponse freeCommentApiResponse = FreeCommentApiResponse.builder()
                .id(freeComment.getId())
                .comment(freeComment.getComment())
                .createdAt(freeComment.getCreatedAt())
                .createdBy(freeComment.getCreatedBy())
                .likes(freeComment.getLikes())
                .isAnonymous(freeComment.getIsAnonymous())
                .freeId(freeComment.getFree().getId())
                .build();

        return freeCommentApiResponse;
    }

    public Header<List<FreeCommentApiResponse>> searchByPost(Long id, Pageable pageable) {
        Page<FreeComment> freeComments = freeCommentRepository.findAllByFreeId(id, pageable);

        List<FreeCommentApiResponse> freeCommentApiResponseList = freeComments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(freeComments.getTotalElements())
                .totalPages(freeComments.getTotalPages())
                .currentElements(freeComments.getNumberOfElements())
                .currentPage(freeComments.getNumber())
                .build();

        return Header.OK(freeCommentApiResponseList, pagination);
    }
}
