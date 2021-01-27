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
@ToString(exclude = {"user", "freeList", "qnaList"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user; // user id

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Free> freeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Qna> qnaList;
}
