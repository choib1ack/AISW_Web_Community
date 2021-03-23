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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("council")
public class Council extends Notice {

    @Builder
    public Council(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                   Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, Account account) {
        super(id, title, writer, content, attachmentFile, status, views, level, firstCategory, secondCategory, account);
    }
}
