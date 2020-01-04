package com.koreahacks.govis.entity.keyword;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "keyword")
public class KeywordEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
}
