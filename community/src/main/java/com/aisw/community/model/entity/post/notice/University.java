package com.aisw.community.model.entity.post.notice;

import com.aisw.community.model.entity.user.User;
import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
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
@DiscriminatorValue("university")
public class University extends Notice {

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Builder
    public University(Long id, String title, String writer, String content, BulletinStatus status,
                      Long views, FirstCategory firstCategory, SecondCategory secondCategory, User user,
                      Campus campus) {
        super(id, title, writer, content, status, views, firstCategory, secondCategory, user);
        this.campus = campus;
    }
}