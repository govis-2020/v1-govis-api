package com.koreahacks.govis.service.auth.impl;

import com.koreahacks.govis.entity.auth.AccessTokenEntity;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.repository.auth.AccessTokenRepository;
import com.koreahacks.govis.service.auth.AuthService;
import com.koreahacks.govis.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Override
    public String issueAccessToken(int userId) throws Exception {

        String accessToken = AuthUtil.generateAccessToken();
        LocalDateTime expiredAt = LocalDateTime.now();
        expiredAt = expiredAt.plusYears(10);

        Optional<List<AccessTokenEntity>> accessTokenEntities =
                accessTokenRepository.findAllByUserIdAndIsEnabled(userId, true);

        if (accessTokenEntities.isPresent()) {
            List<AccessTokenEntity> entities = accessTokenEntities.get();

            for (AccessTokenEntity entity : entities) {
                entity.setEnabled(false);
            }

            accessTokenRepository.saveAll(entities);
        }

        AccessTokenEntity accessTokenEntity = AccessTokenEntity.builder()
                .userId(userId)
                .accessToken(accessToken)
                .expiredAt(Timestamp.valueOf(expiredAt))
                .isEnabled(true)
                .build();

        accessTokenRepository.save(accessTokenEntity);

        return accessToken;
    }

    @Override
    public String verifyAccessToken(String accessToken) throws Exception {

        AccessTokenEntity accessTokenEntity =
                accessTokenRepository.findTopByAccessTokenAndIsEnabled(accessToken, true)
                        .orElseThrow(() -> new GovisException(ReturnCode.UNAUTHORIZED));

        return String.valueOf(accessTokenEntity.getUserId());
    }
}
