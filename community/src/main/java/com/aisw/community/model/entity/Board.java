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
