package com.aisw.community.service;

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
import org.springframework.stereotype.Service;

@Service
public class FreeApiLogicService implements CrudInterface<FreeApiRequest, FreeApiResponse> {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private FreeRepository freeRepository;

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

        Free newFree = freeRepository.save(free);
        return response(newFree);
    }

    @Override
    public Header<FreeApiResponse> read(Long id) {
        return freeRepository.findById(id)
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<FreeApiResponse> update(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        return freeRepository.findById(freeApiRequest.getId())
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
                .map(free -> freeRepository.save(free))
                .map(this::response)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return freeRepository.findById(id)
                .map(free -> {
                    freeRepository.delete(free);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<FreeApiResponse> response(Free free) {
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

        return Header.OK(freeApiResponse);
    }
}
