package com.aisw.community.model.entity.post.notice;

import com.aisw.community.model.entity.user.Account;
import com.aisw.community.model.entity.post.Bulletin;
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

    @Enumerated(EnumType.STRING)
    private SecondCategory category;

    public Notice(Long id, String title, String writer, String content, BulletinStatus status, Long views,
                  FirstCategory firstCategory, SecondCategory secondCategory, Account account) {
        super(id, title, writer, content, status, views, firstCategory, secondCategory, account);
        this.category = secondCategory;
    }
}
