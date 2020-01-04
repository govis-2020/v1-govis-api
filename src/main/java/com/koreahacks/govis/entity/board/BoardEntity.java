package com.koreahacks.govis.entity.board;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Table(name = "board")
public class BoardEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "article_id")
    private int articleId;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "check")
    private boolean check;

    @Column(name = "content")
    private String content;

    @Column(name = "attatchments")
    private String attatchments;

    @Column(name = "created_at")
    private Date createdAt;
}
