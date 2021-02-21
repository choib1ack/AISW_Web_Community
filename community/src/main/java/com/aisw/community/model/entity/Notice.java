package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("notice")
public class Notice extends Bulletin {

    private SecondCategory category;

    public Notice(Long id, String title, String writer, String content, String attachmentFile, BulletinStatus status,
                  Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, User user) {
        super(id, title, writer, content, attachmentFile, status, views, level, firstCategory, secondCategory, user);
        this.category = secondCategory;
    }
}
