package com.aisw.community.service;

import com.aisw.community.controller.CrudController;
import com.aisw.community.ifs.CrudInterface;
import com.aisw.community.model.entity.Department;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.DepartmentApiRequest;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.DepartmentApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.DepartmentRepository;
import com.aisw.community.repository.FreeRepository;
import com.aisw.community.repository.NoticeRepository;
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

    @Override
    public Header<FreeApiResponse> create(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .content(freeApiRequest.getContent())
                .attachmentFile(freeApiRequest.getAttachmentFile())
                .views(freeApiRequest.getViews())
                .likes(freeApiRequest.getLikes())
                .level(freeApiRequest.getLevel())
                .board(boardRepository.getOne(freeApiRequest.getBoardId()))
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
                            .setViews(freeApiRequest.getViews())
                            .setLevel(freeApiRequest.getLevel())
                            .setLikes(freeApiRequest.getLikes())
                            .setBoard(boardRepository.getOne(freeApiRequest.getBoardId()));

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
                .createdAt(free.getCreatedAt())
                .createdBy(free.getCreatedBy())
                .updatedAt(free.getUpdatedAt())
                .updatedBy(free.getUpdatedBy())
                .views(free.getViews())
                .level(free.getLevel())
                .likes(free.getLikes())
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
