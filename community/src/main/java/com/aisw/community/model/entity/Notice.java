package com.aisw.community.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user"})
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 학교 공지 0, 학과 공지1, 학생회 공지2
    private Long category;

    @ManyToOne
    private User user; // user id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<University> universityList;
}
