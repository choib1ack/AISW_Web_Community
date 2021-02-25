package com.aisw.community.service;

import com.aisw.community.model.entity.Free;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.BoardApiResponse;
import com.aisw.community.model.network.response.FreeApiResponse;
import com.aisw.community.repository.FreeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeApiLogicService extends PostService<FreeApiRequest, BoardApiResponse, FreeApiResponse, Free> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreeRepository freeRepository;

    @Override
    public Header<FreeApiResponse> create(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();

        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .writer(userRepository.getOne(freeApiRequest.getUserId()).getName())
                .content(freeApiRequest.getContent())
                .attachmentFile(freeApiRequest.getAttachmentFile())
                .status(freeApiRequest.getStatus())
                .views(0L)
                .likes(0L)
                .isAnonymous(freeApiRequest.getIsAnonymous())
                .level(freeApiRequest.getLevel())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .user(userRepository.getOne(freeApiRequest.getUserId()))
                .build();

        Free newFree = baseRepository.save(free);
        return Header.OK(response(newFree));
    }

    @Override
    @Transactional
    public Header<FreeApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> baseRepository.save((Free)free))
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
                            .setLevel(freeApiRequest.getLevel());
                    free.setIsAnonymous(freeApiRequest.getIsAnonymous());
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
                .writer(free.getWriter())
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
                .category(free.getCategory())
                .userId(free.getUser().getId())
                .build();

        return freeApiResponse;
    }

    @Override
    public Header<List<BoardApiResponse>> search(Pageable pageable) {
        Page<Free> frees = baseRepository.findAll(pageable);

        return getListHeader(frees);
    }

    @Override
    public Header<List<BoardApiResponse>> searchByWriter(String writer, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByWriterContaining(writer, pageable);

        return getListHeader(frees);
    }

    @Override
    public Header<List<BoardApiResponse>> searchByTitle(String title, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByTitleContaining(title, pageable);

        return getListHeader(frees);
    }

    @Override
    public Header<List<BoardApiResponse>> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Free> frees = freeRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);

        return getListHeader(frees);
    }

    private Header<List<BoardApiResponse>> getListHeader(Page<Free> frees) {
        List<BoardApiResponse> boardApiResponseList = frees.stream()
                .map(free -> BoardApiResponse.builder()
                        .id(free.getId())
                        .title(free.getTitle())
                        .category(free.getCategory())
                        .createdAt(free.getCreatedAt())
                        .status(free.getStatus())
                        .views(free.getViews())
                        .writer(free.getWriter())
                        .build())
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(frees.getTotalElements())
                .totalPages(frees.getTotalPages())
                .currentElements(frees.getNumberOfElements())
                .currentPage(frees.getNumber())
                .build();

        return Header.OK(boardApiResponseList, pagination);
    }

    @Transactional
    public Header<FreeApiResponse> pressLikes(Long id) {
        return baseRepository.findById(id)
                .map(free -> free.setLikes(free.getLikes() + 1))
                .map(free -> baseRepository.save((Free)free))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }
}
