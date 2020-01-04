package com.koreahacks.govis.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "user_keyword")
public class UserKeywordEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_keyword_id")
    private int userKeywordId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "is_enabled")
    private boolean isEnabled;
}
