package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BoardCategory;
import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("qna")
public class Qna extends Board {

    private String subject;

    @Builder
    public Qna(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
               Long views, Long likes, Long level, Boolean isAnonymous, BoardCategory category, User user,
               String subject) {
        super(id, title, writer, content, attachmentFile, status, views, likes, level, isAnonymous, category, user);
        this.subject = subject;
    }
}
