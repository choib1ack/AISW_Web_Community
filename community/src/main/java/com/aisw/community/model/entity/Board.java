package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BoardCategory;
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

@Entity
@Getter
@Setter
@ToString(exclude = {"user"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String writer;

    private String content;

    private String attachmentFile;

    @Enumerated(EnumType.STRING)
    private BulletinStatus status;

    private Long views;

    private Long likes;

    private Long level;

    private Boolean isAnonymous;

    private BoardCategory category;

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

    public Board(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                 Long views, Long likes, Long level, Boolean isAnonymous, BoardCategory category, User user) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.attachmentFile = attachmentFile;
        this.status = status;
        this.views = views;
        this.likes = likes;
        this.level = level;
        this.isAnonymous = isAnonymous;
        this.category = category;
        this.user = user;
    }
}
