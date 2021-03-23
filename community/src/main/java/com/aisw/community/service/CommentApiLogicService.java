package com.aisw.community.service;

import com.aisw.community.advice.exception.CommentNotFoundException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Comment;
import com.aisw.community.model.entity.User;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        User user = userRepository.findById(commentApiRequest.getUserId()).orElseThrow(UserNotFoundException::new);
        Board board = boardRepository.findById(commentApiRequest.getBoardId()).orElseThrow(PostNotFoundException::new);
        Comment superComment = commentApiRequest.getSuperCommentId() != null ?
                commentRepository.findById(commentApiRequest.getSuperCommentId())
                        .orElseThrow(CommentNotFoundException::new) : null;
        Comment comment = Comment.builder()
                .writer(user.getName())
                .content(commentApiRequest.getContent())
                .likes(0L)
                .isAnonymous(commentApiRequest.getIsAnonymous())
                .isDeleted(false)
                .board(board)
                .user(user)
                .superComment(superComment)
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
                .userId(comment.getUser().getId())
                .build();

        return freeCommentApiResponse;
    }

    public Header<List<CommentApiResponse>> searchByPost(Long id) {
        List<Comment> comments = commentRepository.findCommentByBoardId(id);

        List<CommentApiResponse> commentApiResponseList = new ArrayList<>();
        Map<Long, CommentApiResponse> map = new HashMap<>();
        comments.stream().forEach(comment -> {
            CommentApiResponse commentApiResponse = CommentApiResponse.convertCommentToDto(comment);
            map.put(commentApiResponse.getId(), commentApiResponse);
            if(comment.getSuperComment() != null) map.get(comment.getSuperComment().getId()).getSubComment().add(commentApiResponse);
            else commentApiResponseList.add(commentApiResponse);
        });
        return Header.OK(commentApiResponseList);
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
