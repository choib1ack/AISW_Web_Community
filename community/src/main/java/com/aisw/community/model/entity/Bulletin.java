package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
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
@ToString(exclude = {"account"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Bulletin {

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

    // 학생회 공지1
    private Long level;

    private FirstCategory firstCategory;

    private SecondCategory secondCategory;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account; // user id

    public Bulletin(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                    Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, Account account) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.attachmentFile = attachmentFile;
        this.status = status;
        this.views = views;
        this.level = level;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.account= account;
    }
}
