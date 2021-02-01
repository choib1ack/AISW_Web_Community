package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"user", "board", "qnaCommentList"})
@EntityListeners(AuditingEntityListener.class)
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String attachmentFile;

    @Enumerated(EnumType.STRING)
    private BulletinStatus status;

    private Long views;

    private Long likes;

    private String subject;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;
    
    // 질문게시판 1
    private Long level;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user id

    @OneToOne
    private Board board; // board id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "qna")
    private List<QnaComment> qnaCommentList;
}
