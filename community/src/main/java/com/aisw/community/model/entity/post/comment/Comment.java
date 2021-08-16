package com.aisw.community.model.entity.post.comment;

import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.entity.post.board.Board;
import com.aisw.community.model.entity.user.Alert;
import com.aisw.community.model.entity.user.User;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"board", "user", "superComment", "subComment", "contentLikeList", "alertList"})
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

    @ColumnDefault("0")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ContentLike> contentLikeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Alert> alertList;

    @PrePersist
    public void prePersist() {
        likes = likes == null ? 0L : likes;
    }
}
