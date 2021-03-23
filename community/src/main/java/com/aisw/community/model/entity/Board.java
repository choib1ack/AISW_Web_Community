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
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"commentList"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("board")
public class Board extends Bulletin {

    private Long likes;

    private Boolean isAnonymous;

    private SecondCategory category;

    @OneToMany
    private List<Comment> commentList;

    public Board(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                 Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, Account account,
                 Long likes, Boolean isAnonymous) {
        super(id, title, writer, content, attachmentFile, status, views, level, firstCategory, secondCategory, account);
        this.likes = likes;
        this.isAnonymous = isAnonymous;
        this.category = secondCategory;
    }
}
