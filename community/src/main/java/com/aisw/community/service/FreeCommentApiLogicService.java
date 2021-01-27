package com.aisw.community.service;

import com.aisw.community.model.entity.FreeComment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeCommentApiRequest;
import com.aisw.community.model.network.response.FreeCommentApiResponse;
import com.aisw.community.repository.FreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeCommentApiLogicService extends BaseService<FreeCommentApiRequest, FreeCommentApiResponse, FreeComment> {

    @Autowired
    private FreeRepository freeRepository;

    @Override
    public Header<FreeCommentApiResponse> create(Header<FreeCommentApiRequest> request) {
        FreeCommentApiRequest freeCommentApiRequest = request.getData();

        FreeComment freeComment = FreeComment.builder()
                .content(freeCommentApiRequest.getContent())
                .likes(freeCommentApiRequest.getLikes())
                .isAnonymous(freeCommentApiRequest.getIsAnonymous())
                .free(freeRepository.getOne(freeCommentApiRequest.getFreeId()))
                .build();

        FreeComment newFreeComment = baseRepository.save(freeComment);
        return Header.OK(response(newFreeComment));
    }

    @Override
    public Header<FreeCommentApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<FreeCommentApiResponse> update(Header<FreeCommentApiRequest> request) {
        FreeCommentApiRequest freeCommentApiRequest = request.getData();

        return baseRepository.findById(freeCommentApiRequest.getId())
                .map(freeComment -> {
                    freeComment
                            .setContent(freeCommentApiRequest.getContent())
                            .setLikes(freeCommentApiRequest.getLikes())
                            .setIsAnonymous(freeCommentApiRequest.getIsAnonymous())
                            .setFree(freeRepository.getOne(freeCommentApiRequest.getFreeId()));

                    return freeComment;
                })
                .map(freeComment -> baseRepository.save(freeComment))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(freeComment -> {
                    baseRepository.delete(freeComment);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private FreeCommentApiResponse response(FreeComment freeComment) {
        FreeCommentApiResponse freeCommentApiResponse = FreeCommentApiResponse.builder()
                .id(freeComment.getId())
                .content(freeComment.getContent())
                .createdAt(freeComment.getCreatedAt())
                .createdBy(freeComment.getCreatedBy())
                .likes(freeComment.getLikes())
                .isAnonymous(freeComment.getIsAnonymous())
                .freeId(freeComment.getFree().getId())
                .build();

        return freeCommentApiResponse;
    }

    @Override
    public Header<List<FreeCommentApiResponse>> search(Pageable pageable) {
        Page<FreeComment> freeComments = baseRepository.findAll(pageable);

        List<FreeCommentApiResponse> freeCommentApiResponseList = freeComments.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(freeCommentApiResponseList);
    }
}
