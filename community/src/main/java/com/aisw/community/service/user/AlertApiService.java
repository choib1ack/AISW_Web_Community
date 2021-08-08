package com.aisw.community.service.user;

import com.aisw.community.advice.exception.AlertNotFoundException;
import com.aisw.community.advice.exception.CommentNotFoundException;
import com.aisw.community.advice.exception.ContentLikeNotFoundException;
import com.aisw.community.advice.exception.NotEqualUserException;
import com.aisw.community.config.auth.PrincipalDetails;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.user.Alert;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.AlertCategory;
import com.aisw.community.model.network.Header;
import com.aisw.community.model.network.Pagination;
import com.aisw.community.model.network.request.user.AlertApiRequest;
import com.aisw.community.model.network.response.user.AlertApiResponse;
import com.aisw.community.repository.post.comment.CommentRepository;
import com.aisw.community.repository.post.like.ContentLikeRepository;
import com.aisw.community.repository.user.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertApiService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ContentLikeRepository contentLikeRepository;

    @Transactional
    public Header<AlertApiResponse> create(Authentication authentication, AlertApiRequest alertApiRequest) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();

        Alert alert = Alert.builder().user(user).build();
        boolean isComment = false;
        if(alertApiRequest.getCommentId() != null) {
            Comment comment = commentRepository.findById(alertApiRequest.getCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(alertApiRequest.getCommentId()));
            alert.setComment(comment);
            isComment = true;
        } else if(alertApiRequest.getContentLikeId() != null) {
            ContentLike contentLike = contentLikeRepository.findById(alertApiRequest.getContentLikeId())
                    .orElseThrow(() -> new ContentLikeNotFoundException(alertApiRequest.getContentLikeId()));
            alert.setContentLike(contentLike);
        }
        Alert newAlert = alertRepository.save(alert);

        return Header.OK(response(newAlert, isComment));
    }

    public Header<List<AlertApiResponse>> readAllAlert(Authentication authentication, Pageable pageable) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Page<Alert> alerts = alertRepository.findAllByUserId(principal.getUser().getId(), pageable);

        List<AlertApiResponse> alertList = new ArrayList<>();
        alerts.stream().forEach(alert -> {
            if(alert.getComment() != null) alertList.add(response(alert, true));
            else alertList.add(response(alert, false));
        });

        Pagination pagination = Pagination.builder()
                .totalElements(alerts.getTotalElements())
                .totalPages(alerts.getTotalPages())
                .currentElements(alerts.getNumberOfElements())
                .currentPage(alerts.getNumber())
                .build();

        return Header.OK(alertList, pagination);
    }


    public Header<AlertApiResponse> checkAlert(Authentication authentication, Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Alert alert = alertRepository.findById(id).orElseThrow(() -> new AlertNotFoundException(id));
        if(alert.getUser().getId() != principal.getUser().getId()) {
            throw new NotEqualUserException(id);
        }

        Alert updatedAlert = alert.setChecked(true);
        boolean isComment = false;
        if(updatedAlert.getComment() != null) isComment = true;

        return Header.OK(response(updatedAlert, isComment));
    }

    private AlertApiResponse response(Alert alert, Boolean isComment) {
        AlertApiResponse alertApiResponse = AlertApiResponse.builder()
                .id(alert.getId())
                .checked(alert.getChecked())
                .createdAt(alert.getCreatedAt())
                .build();
        if(isComment) {
            Comment comment = alert.getComment();
            if(comment.getSuperComment() == null) {
                alertApiResponse.setAlertCategory(AlertCategory.COMMENT);
            } else {
                alertApiResponse.setAlertCategory(AlertCategory.NESTED_COMMENT);
            }
            alertApiResponse
                    .setFirstCategory(comment.getBoard().getFirstCategory())
                    .setSecondCategory(comment.getBoard().getSecondCategory())
                    .setPostId(comment.getBoard().getId());
        } else {
            ContentLike contentLike = alert.getContentLike();
            if(contentLike.getComment() != null) {
                alertApiResponse
                        .setAlertCategory(AlertCategory.LIKE_COMMENT)
                        .setFirstCategory(contentLike.getComment().getBoard().getFirstCategory())
                        .setSecondCategory(contentLike.getComment().getBoard().getSecondCategory())
                        .setPostId(contentLike.getComment().getBoard().getId());
            } else if(contentLike.getBoard() != null) {
                alertApiResponse
                        .setAlertCategory(AlertCategory.LIKE_POST)
                        .setFirstCategory(contentLike.getBoard().getFirstCategory())
                        .setSecondCategory(contentLike.getBoard().getSecondCategory())
                        .setPostId(contentLike.getBoard().getId());
            }
        }
        return alertApiResponse;
    }
}
