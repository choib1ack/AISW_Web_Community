package com.aisw.community.service;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeApiLogicService extends BaseService<FreeApiRequest, FreeApiResponse, Free> {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardApiLogicService boardApiLogicService;

    @Override
    public Header<FreeApiResponse> create(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        BoardApiResponse boardApiResponse = boardApiLogicService.create().getData();

        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .content(freeApiRequest.getContent())
                .attachmentFile(freeApiRequest.getAttachmentFile())
                .status(freeApiRequest.getStatus())
                .views(freeApiRequest.getViews())
                .likes(freeApiRequest.getLikes())
                .isAnonymous(freeApiRequest.getIsAnonymous())
                .level(freeApiRequest.getLevel())
                .user(userRepository.getOne(freeApiRequest.getUserId()))
                .board(boardRepository.getOne(boardApiResponse.getId()))
                .build();

        Free newFree = baseRepository.save(free);
        return Header.OK(response(newFree));
    }

    @Override
    public Header<FreeApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<FreeApiResponse> update(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        return baseRepository.findById(freeApiRequest.getId())
                .map(free -> {
                    free
                            .setTitle(freeApiRequest.getTitle())
                            .setContent(freeApiRequest.getContent())
                            .setAttachmentFile(freeApiRequest.getAttachmentFile())
                            .setStatus(freeApiRequest.getStatus())
                            .setViews(freeApiRequest.getViews())
                            .setLevel(freeApiRequest.getLevel())
                            .setLikes(freeApiRequest.getLikes())
                            .setIsAnonymous(freeApiRequest.getIsAnonymous());

                    return free;
                })
                .map(free -> baseRepository.save(free))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(free -> {
                    boardRepository.findById(free.getBoard().getId())
                            .map(board -> {
                                boardRepository.delete(board);
                                return Header.OK();
                            })
                            .orElseGet(() -> Header.ERROR("데이터 없음"));
                    baseRepository.delete(free);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private FreeApiResponse response(Free free) {
        FreeApiResponse freeApiResponse = FreeApiResponse.builder()
                .id(free.getId())
                .title(free.getTitle())
                .content(free.getContent())
                .attachmentFile(free.getAttachmentFile())
                .status(free.getStatus())
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .level(free.getLevel())
                .likes(free.getLikes())
                .isAnonymous(free.getIsAnonymous())
                .userId(free.getUser().getId())
                .boardId(free.getBoard().getId())
                .build();

        return freeApiResponse;
    }

    @Override
    public Header<List<FreeApiResponse>> search(Pageable pageable) {
        Page<Free> frees = baseRepository.findAll(pageable);

        List<FreeApiResponse> freeApiResponseList = frees.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(freeApiResponseList);
    }
}
