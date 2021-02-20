package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BoardCategory;
import com.aisw.community.model.enumclass.BulletinStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("free")
public class Free extends Board {

    @Builder
    public Free(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                Long views, Long likes, Long level, Boolean isAnonymous, BoardCategory category, User user) {
        super(id, title, writer, content, attachmentFile, status, views, likes, level, isAnonymous, category, user);
    }
}
