package com.koreahacks.govis.entity.board;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
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

    @Column(name = "attachments")
    private String attachments;

    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    private String keyword;
}
