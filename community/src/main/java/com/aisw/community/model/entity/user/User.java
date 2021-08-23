package com.aisw.community.model.entity.user;

import com.aisw.community.model.entity.post.Bulletin;
import com.aisw.community.model.entity.post.comment.Comment;
import com.aisw.community.model.entity.post.like.ContentLike;
import com.aisw.community.model.enumclass.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"bulletinList", "commentList", "contentLikeList", "alertList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String departmentName;

    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Bulletin> bulletinList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ContentLike> contentLikeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Alert> alertList;


    public void update(String name, String email, String phoneNumber, Grade grade, Gender gender, String departmentName) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.gender = gender;
        this.departmentName = departmentName;
    }


}
