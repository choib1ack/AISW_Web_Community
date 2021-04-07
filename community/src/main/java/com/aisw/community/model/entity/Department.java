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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("department")
public class Department extends Notice {

    @Builder
    public Department(Long id, String title, String writer, String content, BulletinStatus status, Long views,
                      FirstCategory firstCategory, SecondCategory secondCategory, Account account) {
        super(id, title, writer, content, status, views, firstCategory, secondCategory, account);
    }
}
