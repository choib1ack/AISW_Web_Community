package com.aisw.community.service.post.like;

import com.aisw.community.advice.exception.CommentNotFoundException;
import com.aisw.community.advice.exception.PostNotFoundException;
import com.aisw.community.advice.exception.UserNotFoundException;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.like.ContentLikeApiRequest;
import com.aisw.community.model.network.response.post.like.ContentLikeApiResponse;
import com.aisw.community.repository.user.UserRepository;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContentLikeApiLogicService {

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Header<ContentLikeApiResponse> pressLike(Header<ContentLikeApiRequest> request) {
        ContentLikeApiRequest contentLikeApiRequest = request.getData();
        User user = userRepository.findById(contentLikeApiRequest.getAccountId())
                .orElseThrow(() -> new UserNotFoundException(contentLikeApiRequest.getAccountId()));
        ContentLike newContentLike = null;
        if(contentLikeApiRequest.getBoardId() == null) {
            Comment comment = commentRepository.findById(contentLikeApiRequest.getCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(contentLikeApiRequest.getCommentId()));
            contentLikeRepository.findContentLikeByUserIdAndCommentId(user.getId(), comment.getId())
                    .ifPresent(contentLike -> {throw new RuntimeException();});

            ContentLike contentLike = ContentLike.builder()
                    .user(user)
                    .comment(comment)
                    .build();

            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
            newContentLike = contentLikeRepository.save(contentLike);
        }
        else if(contentLikeApiRequest.getCommentId() == null) {
            Board board = boardRepository.findById(contentLikeApiRequest.getBoardId())
                    .orElseThrow(() -> new PostNotFoundException(contentLikeApiRequest.getBoardId()));
            contentLikeRepository.findContentLikeByUserIdAndBoardId(user.getId(), board.getId())
                    .ifPresent(contentLike -> {throw new RuntimeException();});

            ContentLike contentLike = ContentLike.builder()
                    .user(user)
                    .board(board)
                    .build();

            board.setLikes(board.getLikes() + 1);
            boardRepository.save(board);
            newContentLike = contentLikeRepository.save(contentLike);
        }
        return Header.OK(response(newContentLike));
    }

    @Transactional
    public Header removeLike(Header<ContentLikeApiRequest> request) {
        ContentLikeApiRequest contentLikeApiRequest = request.getData();
        User user = userRepository.findById(contentLikeApiRequest.getAccountId())
                .orElseThrow(() -> new UserNotFoundException(contentLikeApiRequest.getAccountId()));

        if(contentLikeApiRequest.getBoardId() == null) {
            Comment comment = commentRepository.findById(contentLikeApiRequest.getCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(contentLikeApiRequest.getCommentId()));
            return contentLikeRepository
                    .findContentLikeByUserIdAndCommentId(user.getId(), comment.getId())
                    .map(contentLike -> {
                        comment.setLikes(comment.getLikes() - 1);
                        commentRepository.save(comment);
                        contentLikeRepository.delete(contentLike);
                        return Header.OK();
                    })
                    .orElseGet(() -> Header.ERROR("좋아요 안 눌림"));
        }
        else if(contentLikeApiRequest.getCommentId() == null) {
            Board board = boardRepository.findById(contentLikeApiRequest.getBoardId())
                    .orElseThrow(() -> new PostNotFoundException(contentLikeApiRequest.getBoardId()));
            return contentLikeRepository
                    .findContentLikeByUserIdAndBoardId(user.getId(), board.getId())
                    .map(contentLike -> {
                        board.setLikes(board.getLikes() - 1);
                        boardRepository.save(board);
                        contentLikeRepository.delete(contentLike);
                        return Header.OK();
                    })
                    .orElseGet(() -> Header.ERROR("좋아요 안 눌림"));
        }
        return Header.ERROR("request error");
    }

    private ContentLikeApiResponse response(ContentLike contentLike) {
        ContentLikeApiResponse contentLikeApiResponse = ContentLikeApiResponse.builder()
                .id(contentLike.getId())
                .accountId(contentLike.getUser().getId())
                .build();
        if(contentLike.getBoard() != null) {
            contentLikeApiResponse.setBoardId(contentLike.getBoard().getId());
        } else if(contentLike.getComment() != null) {
            contentLikeApiResponse.setCommentId(contentLike.getComment().getId());
        }
        return contentLikeApiResponse;
    }
}
