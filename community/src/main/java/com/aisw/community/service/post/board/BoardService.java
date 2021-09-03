package com.aisw.community.service.post.board;

import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.response.post.board.BoardApiResponse;
import com.aisw.community.model.network.response.post.board.BoardResponseDTO;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.service.post.AbsBulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService extends AbsBulletinService<BoardResponseDTO, Board> {

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Cacheable(value = "boardReadAll", key = "#pageable.pageNumber")
    public Header<BoardResponseDTO> readAll(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    @Cacheable(value = "boardSearchByWriter",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#writer, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByWriter(String writer, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByWriterContaining(writer, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    @Cacheable(value = "boardSearchByTitle",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitle(String title, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTitleContaining(title, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    @Override
    @Cacheable(value = "boardSearchByTitleOrContent",
            key = "T(com.aisw.community.component.util.KeyCreatorBean).createKey(#title, #content, #pageable.pageNumber)")
    public Header<BoardResponseDTO> searchByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByTitleContainingOrContentContaining(title, content, pageable);
        Page<Board> boardsByStatus = searchByStatus(pageable);

        return getListHeader(boards, boardsByStatus);
    }

    public Page<Board> searchByStatus(Pageable pageable) {
        Page<Board> boards = boardRepository.findAllByStatusIn(
                Arrays.asList(BulletinStatus.URGENT, BulletinStatus.NOTICE), pageable);

        return boards;
    }

    private Header<BoardResponseDTO> getListHeader
            (Page<Board> boards, Page<Board> boardsByStatus) {
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.builder()
                .boardApiResponseList(boards.stream()
                        .map(board -> BoardApiResponse.builder()
                                .id(board.getId())
                                .title(board.getTitle())
                                .category(board.getCategory())
                                .createdAt(board.getCreatedAt())
                                .status(board.getStatus())
                                .views(board.getViews())
                                .writer(board.getWriter())
//                                .hasFile((board.getFileList().size() != 0) ? true : false)
                                .build())
                        .collect(Collectors.toList()))
                .build();
        List<BoardApiResponse> boardApiNoticeResponseList = new ArrayList<>();
        List<BoardApiResponse> boardApiUrgentResponseList = new ArrayList<>();
        boardsByStatus.stream().forEach(board -> {
            BoardApiResponse boardApiResponse = BoardApiResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .createdAt(board.getCreatedAt())
                    .status(board.getStatus())
                    .views(board.getViews())
                    .writer(board.getWriter())
//                    .hasFile((board.getFileList().size() != 0) ? true : false)
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
                .totalElements(boards.getTotalElements())
                .totalPages(boards.getTotalPages())
                .currentElements(boards.getNumberOfElements())
                .currentPage(boards.getNumber())
                .build();

        return Header.OK(boardResponseDTO, pagination);
    }
}
