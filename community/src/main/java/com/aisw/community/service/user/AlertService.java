package com.aisw.community.service.user;

import com.aisw.community.component.advice.exception.AlertNotFoundException;
import com.aisw.community.component.advice.exception.CommentNotFoundException;
import com.aisw.community.component.advice.exception.ContentLikeNotFoundException;
import com.aisw.community.component.advice.exception.NotEqualUserException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlertService {

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

        Alert alert = Alert.builder().user(user).firstCategory(alertApiRequest.getFirstCategory())
                .secondCategory(alertApiRequest.getSecondCategory()).postId(alertApiRequest.getPostId()).build();

        if(alertApiRequest.getCommentId() != null) {
            Comment comment = commentRepository.findById(alertApiRequest.getCommentId())
                    .orElseThrow(() -> new CommentNotFoundException(alertApiRequest.getCommentId()));
            alert.setComment(comment);
            if(comment.getSuperComment() == null) alert.setAlertCategory(AlertCategory.COMMENT);
            else alert.setAlertCategory(AlertCategory.NESTED_COMMENT);
        } else if(alertApiRequest.getContentLikeId() != null) {
            ContentLike contentLike = contentLikeRepository.findById(alertApiRequest.getContentLikeId())
                    .orElseThrow(() -> new ContentLikeNotFoundException(alertApiRequest.getContentLikeId()));
            alert.setContentLike(contentLike);
            if(contentLike.getBoard() != null) alert.setAlertCategory(AlertCategory.LIKE_POST);
            else if(contentLike.getComment() != null) alert.setAlertCategory(AlertCategory.LIKE_COMMENT);
        }
        Alert newAlert = alertRepository.save(alert);
        return Header.OK(response(newAlert));
    }

    public Header<List<AlertApiResponse>> readAllAlert(Authentication authentication, Pageable pageable) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Page<Alert> alerts = alertRepository.findAllByUserId(principal.getUser().getId(), pageable);
        List<AlertApiResponse> alertApiResponseList = alerts.stream().map(this::response).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(alerts.getTotalElements())
                .totalPages(alerts.getTotalPages())
                .currentElements(alerts.getNumberOfElements())
                .currentPage(alerts.getNumber())
                .build();

        return Header.OK(alertApiResponseList, pagination);
    }


    public Header<AlertApiResponse> checkAlert(Authentication authentication, Long id) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        Alert alert = alertRepository.findById(id).orElseThrow(() -> new AlertNotFoundException(id));
        if(alert.getUser().getId() != principal.getUser().getId()) {
            throw new NotEqualUserException(id);
        }

        alert.setChecked(true);
        Alert updatedAlert = alertRepository.save(alert);

        return Header.OK(response(updatedAlert));
    }

    private AlertApiResponse response(Alert alert) {
        AlertApiResponse alertApiResponse = AlertApiResponse.builder()
                .id(alert.getId())
                .firstCategory(alert.getFirstCategory())
                .secondCategory(alert.getSecondCategory())
                .alertCategory(alert.getAlertCategory())
                .postId(alert.getPostId())
                .checked(alert.getChecked())
                .createdAt(alert.getCreatedAt())
                .build();
        return alertApiResponse;
    }
}
