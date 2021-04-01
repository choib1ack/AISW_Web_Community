package com.aisw.community.service;

import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.ContentLike;
import com.aisw.community.model.network.response.ContentLikeApiResponse;
import com.aisw.community.repository.AccountRepository;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.CommentRepository;
import com.aisw.community.repository.ContentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentLikeApiLogicService {

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<ContentLikeApiResponse> readPostLikeByAccount(Long id) {
        List<ContentLike> contentLikeList = contentLikeRepository.findAllByAccountId(id);

        List<ContentLikeApiResponse> contentLikeApiResponseList = new ArrayList<>();
        contentLikeList.stream().forEach(contentLike -> {
            ContentLikeApiResponse contentLikeApiResponse = ContentLikeApiResponse.builder()
                    .id(contentLike.getId())
                    .accountId(contentLike.getAccount().getId())
                    .build();
            if(contentLike.getComment() != null) {
                contentLikeApiResponse.setCommentId(contentLike.getComment().getId());
            }
            else if(contentLike.getBoard() != null) {
                contentLikeApiResponse.setBoardId(contentLike.getBoard().getId());
            }
            contentLikeApiResponseList.add(contentLikeApiResponse);
        });
        return contentLikeApiResponseList;
    }

//    public Header<ContentLikeApiResponse> pressLike(Header<ContentLikeApiRequest> request) {
//        // request가 board인지 comment인지 확인하여 해당 data update
//        // 안 눌려있을 때
//        ContentLikeApiResponse contentLikeApiResponse;
//        ContentLikeApiRequest contentLikeApiRequest = request.getData();
//        if(contentLikeApiRequest.getLikeStatus() == LikeStatus.UNPRESSED) {
//            if(contentLikeApiRequest.getBoardId() != null) {
//                contentLikeApiResponse = createLikeToBoard(contentLikeApiRequest);
//            }
//            else if(contentLikeApiRequest.getCommentId() != null) {
//                Optional<ContentLike> contentLike = contentLikeRepository.findContentLikeByAccountIdAndCommentId(
//                        contentLikeApiRequest.getAccountId(), contentLikeApiRequest.getCommentId());
//                if(!contentLike.isPresent()) {
//                    Optional<Comment> comment = commentRepository.findById(contentLikeApiRequest.getCommentId());
//                    comment.ifPresent(readComment -> {
//                        readComment.setLikes(readComment.getLikes() + 1);
//                        commentRepository.save(readComment);
//                        // create like
//                        ContentLike newContentLike = ContentLike.builder()
//                                .account(accountRepository.getOne(contentLikeApiRequest.getAccountId()))
//                                .comment(commentRepository.getOne(contentLikeApiRequest.getCommentId()))
//                                .build();
//                        newContentLike = contentLikeRepository.save(newContentLike);
//
//                        // create response
//                        ContentLikeApiResponse contentLikeApiResponse = ContentLikeApiResponse.builder()
//                                .id(newContentLike.getId())
//                                .accountId(newContentLike.getAccount().getId())
//                                .commentId(newContentLike.getComment().getId())
//                                .likes(newContentLike.getComment().getLikes())
//                                .build();
//                    });
//                }
//            }
//        }
//        // 이미 눌려있을 때
//        else if(contentLikeApiRequest.getLikeStatus() == LikeStatus.PRESSED) {
//            if(contentLikeApiRequest.getBoardId() != null) {
//                Optional<ContentLike> contentLike = contentLikeRepository.findContentLikeByAccountIdAndBoardId(
//                        contentLikeApiRequest.getAccountId(), contentLikeApiRequest.getBoardId());
//                contentLike.ifPresent(readContentLike -> {
//                    Optional<Board> board = boardRepository.findById(readContentLike.getBoard().getId());
//                    board.ifPresent(readBoard -> {
//                        readBoard.setLikes(readBoard.getLikes() - 1);
//                        boardRepository.save(readBoard);
//                        // delete like
//                        contentLikeRepository.delete(readContentLike);
//                    });
//                });
//            }
//            else if(contentLikeApiRequest.getCommentId() != null) {
//                Optional<ContentLike> contentLike = contentLikeRepository.findContentLikeByAccountIdAndCommentId(
//                        contentLikeApiRequest.getAccountId(), contentLikeApiRequest.getCommentId());
//                contentLike.ifPresent(readContentLike -> {
//                    Optional<Comment> comment = commentRepository.findById(readContentLike.getComment().getId());
//                    comment.ifPresent(readComment -> {
//                        readComment.setLikes(readComment.getLikes() - 1);
//                        commentRepository.save(readComment);
//                        // delete like
//                        contentLikeRepository.delete(readContentLike);
//                    });
//                });
//            }
//        }
//    }
//
//    private ContentLikeApiResponse createLikeToBoard(ContentLikeApiRequest contentLikeApiRequest) {
//        Optional<ContentLike> contentLike = contentLikeRepository.findContentLikeByAccountIdAndBoardId(
//                contentLikeApiRequest.getAccountId(), contentLikeApiRequest.getBoardId());
//        if(!contentLike.isPresent()) {
//            Optional<Board> board = boardRepository.findById(contentLikeApiRequest.getBoardId());
//            board.ifPresent(readBoard -> {
//                readBoard.setLikes(readBoard.getLikes() + 1);
//                boardRepository.save(readBoard);
//                // create like
//                ContentLike newContentLike = ContentLike.builder()
//                        .account(accountRepository.getOne(contentLikeApiRequest.getAccountId()))
//                        .board(boardRepository.getOne(contentLikeApiRequest.getBoardId()))
//                        .build();
//                newContentLike = contentLikeRepository.save(newContentLike);
//
//                // create response
//                 return ContentLikeApiResponse.builder()
//                        .id(newContentLike.getId())
//                        .accountId(newContentLike.getAccount().getId())
//                        .boardId(newContentLike.getBoard().getId())
//                        .likes(newContentLike.getBoard().getLikes())
//                        .build();
//            });
//        }
//    }
}
