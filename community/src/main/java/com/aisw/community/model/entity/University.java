package com.aisw.community.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class University {

    @Id
    @GeneratedValue
    private Long id;

    private Integer level;

    private Integer campus;


}
