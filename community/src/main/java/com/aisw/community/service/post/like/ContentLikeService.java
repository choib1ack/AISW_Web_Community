package com.aisw.community.service.post.like;

import com.aisw.community.component.advice.exception.*;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.request.post.like.ContentLikeApiRequest;
import com.aisw.community.model.network.request.user.AlertApiRequest;
import com.aisw.community.model.network.response.post.like.ContentLikeApiResponse;
import com.aisw.community.repository.post.board.BoardRepository;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import com.aisw.community.service.user.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContentLikeService {

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AlertService alertService;

    @Transactional
    public Header<ContentLikeApiResponse> pressLike(Authentication authentication, Header<ContentLikeApiRequest> request) {
        ContentLikeApiRequest contentLikeApiRequest = request.getData();

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        ContentLike newContentLike;
        AlertApiRequest alertApiRequest = new AlertApiRequest();
        if(contentLikeApiRequest.getBoardId() == null) {
            Comment comment = commentRepository.findByIdFetchBoard(contentLikeApiRequest.getCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(contentLikeApiRequest.getCommentId()));
            Optional<ContentLike> optional = contentLikeRepository
                    .findContentLikeByUserIdAndCommentId(user.getId(), comment.getId());
            if(optional.isPresent()) {
                throw new ContentLikeAlreadyExistException(comment.getId());
            }

            ContentLike contentLike = ContentLike.builder()
                    .user(user)
                    .comment(comment)
                    .build();

            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
            newContentLike = contentLikeRepository.save(contentLike);

            alertApiRequest
                    .setFirstCategory(comment.getBoard().getFirstCategory())
                    .setSecondCategory(comment.getBoard().getSecondCategory())
                    .setPostId(comment.getBoard().getId());
        } else if(contentLikeApiRequest.getCommentId() == null) {
            Board board = boardRepository.findById(contentLikeApiRequest.getBoardId())
                    .orElseThrow(() -> new PostNotFoundException(contentLikeApiRequest.getBoardId()));
            Optional<ContentLike> optional = contentLikeRepository
                    .findContentLikeByUserIdAndBoardId(user.getId(), board.getId());
            if(optional.isPresent()) {
                throw new ContentLikeAlreadyExistException(board.getId());
            }

            ContentLike contentLike = ContentLike.builder()
                    .user(user)
                    .board(board)
                    .build();

            board.setLikes(board.getLikes() + 1);
            boardRepository.save(board);
            newContentLike = contentLikeRepository.save(contentLike);

            alertApiRequest
                    .setFirstCategory(board.getFirstCategory())
                    .setSecondCategory(board.getSecondCategory())
                    .setPostId(board.getId());
        } else {
            throw new WrongRequestException();
        }

        alertApiRequest.setContentLikeId(newContentLike.getId());
        alertService.create(authentication, alertApiRequest);

        return Header.OK(response(newContentLike));
    }

    @Transactional
    public Header removeLike(Authentication authentication, Long id, String target) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        if(target.equals("COMMENT")) {
            Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
            return contentLikeRepository
                    .findContentLikeByUserIdAndCommentId(user.getId(), comment.getId())
                    .map(contentLike -> {
                        comment.setLikes(comment.getLikes() - 1);
                        commentRepository.save(comment);
                        contentLikeRepository.delete(contentLike);
                        return Header.OK();
                    })
                    .orElseThrow(() -> new ContentLikeNotFoundException(comment.getId()));
        }
        else if(target.equals("POST")) {
            Board board = boardRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
            return contentLikeRepository
                    .findContentLikeByUserIdAndBoardId(user.getId(), board.getId())
                    .map(contentLike -> {
                        board.setLikes(board.getLikes() - 1);
                        boardRepository.save(board);
                        contentLikeRepository.delete(contentLike);
                        return Header.OK();
                    })
                    .orElseThrow(() -> new ContentLikeNotFoundException(board.getId()));
        }
        throw new WrongRequestException();
    }

    public List<ContentLike> getContentLikeByUser(Long id) {
        return contentLikeRepository.findAllByUserId(id);
    }

    private ContentLikeApiResponse response(ContentLike contentLike) {
        ContentLikeApiResponse contentLikeApiResponse = ContentLikeApiResponse.builder()
                .id(contentLike.getId())
                .build();
        if(contentLike.getBoard() != null) {
            contentLikeApiResponse.setBoardId(contentLike.getBoard().getId());
        } else if(contentLike.getComment() != null) {
            contentLikeApiResponse.setCommentId(contentLike.getComment().getId());
        }
        return contentLikeApiResponse;
    }
}
