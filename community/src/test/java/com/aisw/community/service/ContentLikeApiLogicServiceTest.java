package com.aisw.community.service;

import com.aisw.community.CommunityApplicationTests;
import com.aisw.community.model.entity.Board;
import com.aisw.community.model.entity.Comment;
import com.aisw.community.model.entity.ContentLike;
import com.aisw.community.model.enumclass.LikeStatus;
import com.aisw.community.model.network.request.ContentLikeApiRequest;
import com.aisw.community.model.network.response.ContentLikeApiResponse;
import com.aisw.community.repository.AccountRepository;
import com.aisw.community.repository.BoardRepository;
import com.aisw.community.repository.CommentRepository;
import com.aisw.community.repository.ContentLikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class ContentLikeApiLogicServiceTest extends CommunityApplicationTests {

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BoardRepository<Board> boardRepository;

    @Autowired
    private CommentRepository commentRepository;

//    @Test
//    public void pressLike() {
//        // board like
//        // request
//        ContentLikeApiRequest contentLikeApiRequest = ContentLikeApiRequest.builder()
//                .likeStatus(LikeStatus.UNPRESSED)
//                .accountId(1L)
////                .boardId(1L)
//                .commentId(1L)
//                .build();
//
//        // request가 board인지 comment인지 확인하여 해당 data update
//        // 안 눌려있을 때
//        if(contentLikeApiRequest.getLikeStatus() == LikeStatus.UNPRESSED) {
//            if(contentLikeApiRequest.getBoardId() != null) {
//                Optional<ContentLike> contentLike = contentLikeRepository.findContentLikeByAccountIdAndBoardId(
//                        contentLikeApiRequest.getAccountId(), contentLikeApiRequest.getBoardId());
//                if(!contentLike.isPresent()) {
//                    Optional<Board> board = boardRepository.findById(contentLikeApiRequest.getBoardId());
//                    board.ifPresent(readBoard -> {
//                        readBoard.setLikes(readBoard.getLikes() + 1);
//                        boardRepository.save(readBoard);
//                        // create like
//                        ContentLike newContentLike = ContentLike.builder()
//                                .account(accountRepository.getOne(contentLikeApiRequest.getAccountId()))
//                                .board(boardRepository.getOne(contentLikeApiRequest.getBoardId()))
//                                .build();
//                        newContentLike = contentLikeRepository.save(newContentLike);
//
//                        // create response
//                        ContentLikeApiResponse contentLikeApiResponse = ContentLikeApiResponse.builder()
//                                .id(newContentLike.getId())
//                                .accountId(newContentLike.getAccount().getId())
//                                .boardId(newContentLike.getBoard().getId())
//                                .likes(newContentLike.getBoard().getLikes())
//                                .build();
//                        System.out.println(contentLikeApiResponse);
//                    });
//                }
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
//                        System.out.println(contentLikeApiResponse);
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
}