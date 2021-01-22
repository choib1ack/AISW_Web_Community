package com.aisw.community.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 학교 공지 0, 학과 공지 1, 학생회 공지1
    private Long level;

    // 공통 0, 글로벌1,  메디컬2
    private Long campus;

    private Long universityContentId;

    private Long noticeId;
}
