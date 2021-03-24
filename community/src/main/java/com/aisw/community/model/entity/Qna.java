package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
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
               Long views, FirstCategory firstCategory, SecondCategory secondCategory, Account account,
               Long likes, Boolean isAnonymous, String subject) {
        super(id, title, writer, content, attachmentFile, status, views, firstCategory, secondCategory,
                account, likes, isAnonymous);
        this.subject = subject;
    }
}
