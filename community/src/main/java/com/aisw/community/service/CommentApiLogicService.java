package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Comment;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.CommentApiRequest;
import com.aisw.community.model.network.response.CommentApiResponse;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.CommentRepository;
import com.aisw.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentApiLogicService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    public Header<CommentApiResponse> create(Header<CommentApiRequest> request) {
        CommentApiRequest commentApiRequest = request.getData();

        Comment comment = Comment.builder()
                .writer(userRepository.getOne(commentApiRequest.getUserId()).getName())
                .content(commentApiRequest.getContent())
                .likes(0L)
                .isAnonymous(commentApiRequest.getIsAnonymous())
                .board(boardRepository.getOne(commentApiRequest.getBoardId()))
                .account(userRepository.getOne(commentApiRequest.getUserId()))
                .build();

        Comment newComment = commentRepository.save(comment);
        return Header.OK(response(newComment));
    }

    public Header delete(Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private CommentApiResponse response(Comment comment) {
        CommentApiResponse freeCommentApiResponse = CommentApiResponse.builder()
                .id(comment.getId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .likes(comment.getLikes())
                .isAnonymous(comment.getIsAnonymous())
                .boardId(comment.getBoard().getId())
                .userId(comment.getAccount().getId())
                .build();

        return freeCommentApiResponse;
    }

    public Header<List<CommentApiResponse>> searchByPost(Long id, Pageable pageable) {
        Page<Comment> freeComments = commentRepository.findAllByBoardId(id, pageable);

        List<CommentApiResponse> freeCommentApiResponseList = freeComments.stream()
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

    @Transactional
    public Header<CommentApiResponse> pressLikes(Long id) {
        return commentRepository.findById(id)
                .map(comment -> comment.setLikes(comment.getLikes() + 1))
                .map(comment -> commentRepository.save(comment))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }
}
