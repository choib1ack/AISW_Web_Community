package com.aisw.community.model.entity;

import com.aisw.community.model.enumclass.BulletinStatus;
import com.aisw.community.model.enumclass.Campus;
import com.aisw.community.model.enumclass.NoticeCategory;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    public University(Long id, String title, String writer, String content, String attachmentFile,
                      BulletinStatus status, Long views, Long level, NoticeCategory category, User user, Campus campus) {
        super(id, title, writer, content, attachmentFile, status, views, level, category, user);
        this.campus = campus;
    }
}
