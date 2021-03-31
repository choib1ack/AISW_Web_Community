package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.FirstCategory;
import com.aisw.community.model.enumclass.SecondCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("notice")
public class Notice extends Bulletin {

    private SecondCategory category;

    public Notice(Long id, String title, String writer, String content, BulletinStatus status,
                  Long views, Long level, FirstCategory firstCategory, SecondCategory secondCategory, Account account, List<Attachment> attachment) {
        super(id, title, writer, content, status, views, level, firstCategory, secondCategory, account, attachment);
        this.category = secondCategory;
    }
}
