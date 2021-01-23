package com.aisw.community.model.entity;

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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"noticeList", "boardList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phoneNumber;

    private Integer grade;

    private Integer studentId;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    // 가입하지 않으면 0, 재학생 1, 학생회 2, 직원(조교, 교수) 3, 관리자 4
    private Integer level;

    private String job;

    private Integer gender;

    private String university;

    private String college;

    private String department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Notice> noticeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Board> boardList;
}
