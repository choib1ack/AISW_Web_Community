package com.aisw.community.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"notice"})
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 학교 공지 0, 학과 공지 1, 학생회 공지1
    private Long level;

    // 공통 0, 글로벌1,  메디컬2
    private Long campus;

    @ManyToOne
    private Notice notice; // notice id

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "university")
    private UniversityContent universityContent;
}
