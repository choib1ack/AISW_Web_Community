package com.aisw.community.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@ToString(exclude = {"university", "department", "council"})
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "notice")
    private University university;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "notice")
    private Department department;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "notice")
    private Council council;
}
