package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("free")
public class Free extends Board {

    @Builder
    public Free(Long id, String title, String writer, String content, BulletinStatus status,
                Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, Account account,
                List<Attachment> attachment, Long likes, Boolean isAnonymous) {
        super(id, title, writer, content, status, views, level, firstCategory, secondCategory, account, attachment,
                likes, isAnonymous);
    }
}
