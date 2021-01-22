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

    @ManyToOne
    private User user; // user id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<University> universityList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<Department> departmentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "notice")
    private List<Council> councilList;
}
