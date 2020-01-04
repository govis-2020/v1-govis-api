package com.koreahacks.govis.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "access_token")
public class AccessTokenEntity {

    @Id
    @GeneratedValue
    @Column(name = "access_token_id")
    private long accessTokenId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    @Column(name = "is_enabled")
    private boolean isEnabled;

}
