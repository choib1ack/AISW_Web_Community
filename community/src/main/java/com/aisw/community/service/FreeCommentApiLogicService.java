package com.aisw.community.service;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
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

    public Header<List<FreeCommentApiResponse>> searchByFree(Long id, Pageable pageable) {
        Page<FreeComment> freeComments = freeCommentRepository.findAll(pageable);

        List<FreeCommentApiResponse> freeCommentApiResponseList = new ArrayList<>();
        freeComments.stream().forEach(freeComment -> {
                    if(freeComment.getFree().getId() == id) {
                        FreeCommentApiResponse freeCommentApiResponse = response(freeComment);
                        freeCommentApiResponseList.add(freeCommentApiResponse);
                    }
                });

        return Header.OK(freeCommentApiResponseList);
    }
}
