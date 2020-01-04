package com.koreahacks.govis.entity.user;

import com.koreahacks.govis.entity.keyword.KeywordEntity;
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

    @Column(name = "keyword_id")
    private int keywordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private KeywordEntity keywordEntity;

    @Column(name = "is_enabled")
    private boolean isEnabled;
}
