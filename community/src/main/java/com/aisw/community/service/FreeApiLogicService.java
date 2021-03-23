package com.aisw.community.service;

import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.Free;
import com.aisw.community.model.entity.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.FreeApiRequest;
import com.aisw.community.model.network.response.*;
import com.aisw.community.repository.FreeRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FreeApiLogicService extends BoardPostService<FreeApiRequest, BoardResponseDTO, FreeWithCommentApiResponse, FreeApiResponse, Free> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FreeRepository freeRepository;

    @Autowired
    private CommentApiLogicService commentApiLogicService;

    @Override
    public Header<FreeApiResponse> create(Header<FreeApiRequest> request) {
        FreeApiRequest freeApiRequest = request.getData();
        User user = userRepository.findById(freeApiRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        Free free = Free.builder()
                .title(freeApiRequest.getTitle())
                .writer(user.getName())
                .content(freeApiRequest.getContent())
                .attachmentFile(freeApiRequest.getAttachmentFile())
                .status(freeApiRequest.getStatus())
                .views(0L)
                .likes(0L)
                .isAnonymous(freeApiRequest.getIsAnonymous())
                .level(freeApiRequest.getLevel())
                .firstCategory(FirstCategory.BOARD)
                .secondCategory(SecondCategory.FREE)
                .user(user)
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
    @Transactional
    public Header<FreeWithCommentApiResponse> readWithComment(Long id) {
        return baseRepository.findById(id)
                .map(free -> free.setViews(free.getViews() + 1))
                .map(free -> baseRepository.save((Free)free))
                .map(this::responseWithComment)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private FreeWithCommentApiResponse responseWithComment(Free free) {
        FreeWithCommentApiResponse freeWithCommentApiResponse = FreeWithCommentApiResponse.builder()
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
                .commentApiResponseList(commentApiLogicService.searchByPost(free.getId()).getData())
                .build();

        return freeWithCommentApiResponse;
    }

    @Override
    public Header<BoardResponseDTO> search(Pageable pageable) {
        Page<Free> frees = baseRepository.findAll(pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByWriterContaining(writer, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByTitleContaining(title, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    @Override
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Free> frees = freeRepository
                .findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Free> freesByStatus = searchByStatus(pageable);

        return getListHeader(frees, freesByStatus);
    }

    public Page<Free> searchByStatus(Pageable pageable) {
        Page<Free> frees = freeRepository.findAllByStatusOrStatus(
                BulletinStatus.URGENT, BulletinStatus.NOTICE, pageable);

        return frees;
    }

    private Header<BoardResponseDTO> getListHeader
            (Page<Free> frees, Page<Free> freesByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(frees.stream()
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<BoardApiResponse> boardApiNoticeResponseList = new ArrayList<>();
        List<BoardApiResponse> boardApiUrgentResponseList = new ArrayList<>();
        freesByStatus.stream().forEach(board -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .createdAt(board.getCreatedAt())
                    .status(board.getStatus())
                    .views(board.getViews())
                    .writer(board.getWriter())
                    .build();
            if(boardApiResponse.getStatus() == BulletinStatus.NOTICE) {
                boardApiNoticeResponseList.add(boardApiResponse);
            }
            else if(boardApiResponse.getStatus() == BulletinStatus.URGENT) {
                boardApiUrgentResponseList.add(boardApiResponse);
            }
        });
        boardResponseDTO.setBoardApiNoticeResponseList(boardApiNoticeResponseList);
        boardResponseDTO.setBoardApiUrgentResponseList(boardApiUrgentResponseList);

        Pagination pagination = Pagination.builder()
                .totalElements(frees.getTotalElements())
                .totalPages(frees.getTotalPages())
                .currentElements(frees.getNumberOfElements())
                .currentPage(frees.getNumber())
                .build();

        return Header.OK(boardResponseDTO, pagination);
    }

    @Override
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
