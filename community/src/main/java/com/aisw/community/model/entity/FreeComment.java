package com.aisw.community.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"free", "user"})
@EntityListeners(AuditingEntityListener.class)
public class FreeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;

    private String comment;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    // 익명 true, 비익명 false
    private Boolean isAnonymous;

    private Long likes;

    @ManyToOne
    private Free free;

    @ManyToOne
    private User user;
}
