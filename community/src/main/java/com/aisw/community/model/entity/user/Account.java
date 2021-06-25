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
@ToString(exclude = {"bulletinList", "commentList", "contentLikeList"})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String picture;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private Integer studentId;

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

    @Enumerated(EnumType.STRING)
    private Campus university;

    @Enumerated(EnumType.STRING)
    private CollegeName collegeName;

    @Enumerated(EnumType.STRING)
    private DepartmentName departmentName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Bulletin> bulletinList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Comment> commentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
    private Set<ContentLike> contentLikeList;

    @Builder
    public Account(Long id, String name, String email, String picture, String phoneNumber, Grade grade, Integer studentId, Gender gender, Campus university, CollegeName collegeName, DepartmentName departmentName,
                   LocalDateTime createdAt,LocalDateTime updatedAt,String createdBy,String updatedBy,UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.studentId = studentId;
        this.role = role;
        this.gender = gender;
        this.university = university;
        this.collegeName = collegeName;
        this.departmentName = departmentName;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;

    }


    public Account update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getUserRoleKey() {
        return this.role.getKey();
    }


}
