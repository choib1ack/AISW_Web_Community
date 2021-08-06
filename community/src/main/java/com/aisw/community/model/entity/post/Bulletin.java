package com.aisw.community.model.entity.post;

import com.aisw.community.model.entity.post.file.File;
import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"user"})
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

    @Enumerated(EnumType.STRING)
    private BulletinStatus status;

    @ColumnDefault("0")
    private Long views;

    @Enumerated(EnumType.STRING)
    private FirstCategory firstCategory;

    @Enumerated(EnumType.STRING)
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
    private User user; // user id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bulletin", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<File> fileList;

    public Bulletin(Long id, String title, String writer, String content, BulletinStatus status,
                    Long views, FirstCategory firstCategory, SecondCategory secondCategory, User user) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.status = status;
        this.views = views;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        views = views == null ? 0L : views;
    }
}
