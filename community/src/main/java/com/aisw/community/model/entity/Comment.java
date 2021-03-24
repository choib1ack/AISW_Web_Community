package com.aisw.community.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Columns;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"board", "user", "superComment", "subComment"})
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Boolean isDeleted;

    private Long likes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "super_comment_id")
    private Comment superComment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "superComment", orphanRemoval = true)
    private List<Comment> subComment;
}
