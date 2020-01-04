package com.koreahacks.govis.service.user.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.User;
import com.koreahacks.govis.entity.user.AccessTokenEntity;
import com.koreahacks.govis.entity.user.UserEntity;
import com.koreahacks.govis.entity.user.UserKeywordEntity;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.repository.user.AccessTokenRepository;
import com.koreahacks.govis.repository.user.UserKeywordRepository;
import com.koreahacks.govis.repository.user.UserRepository;
import com.koreahacks.govis.service.user.UserService;
import com.koreahacks.govis.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${google.client.android}")
    private String googleClientAndroid;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    private UserKeywordRepository userKeywordRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Login.Info login(String idToken) throws Exception {

        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Arrays.asList(googleClientAndroid))
                .build();

        GoogleIdToken googleIdToken;

        try {
            googleIdToken = verifier.verify(idToken);
        } catch (Exception e) {
            throw new GovisException(ReturnCode.UNAUTHORIZED.getCode(), ReturnCode.UNAUTHORIZED.getMessage());
        }

        if (googleIdToken == null) {
            throw new GovisException(ReturnCode.UNAUTHORIZED.getCode(), ReturnCode.UNAUTHORIZED.getMessage());
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        if (payload == null) {
            throw new GovisException(ReturnCode.NO_DATA_IN_GOOGLE.getCode(), ReturnCode.NO_DATA_IN_GOOGLE.getMessage());
        }

        Optional<UserEntity> userEntity = userRepository.findTopByEmailAndIsEnabled(payload.getEmail(), true);

        // 유저정보 없을 시 회원가입 처리
        if (userEntity.isPresent()) {
            userEntity = signUp(User.SignUp.builder()
                    .email(payload.getEmail())
                    .userName((String) payload.get("name"))
                    .build());
        }

        return Login.Info.builder()
                .accessToken(issueAccessToken(userEntity.get().getUserId()))
                .userInfo(modelMapper.map(userEntity, User.Info.class))
                .build();
    }

    @Override
    public Optional<UserEntity> signUp(User.SignUp signUp) throws Exception {

        UserEntity userEntity = modelMapper.map(signUp, UserEntity.class);

        return Optional.ofNullable(userRepository.save(userEntity));
    }

    @Override
    public String issueAccessToken(int userId) throws Exception {

        String accessToken = AuthUtil.generateAccessToken();
        LocalDateTime expiredAt = LocalDateTime.now();
        expiredAt = expiredAt.plusYears(10);

        Optional<List<AccessTokenEntity>> accessTokenEntities =
                accessTokenRepository.findAllByUserIdAndIsEnabled(userId, true);

        if (!accessTokenEntities.isPresent()) {
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
    public List<String> getUserKeywords(int userId) throws Exception {

        List<UserKeywordEntity> userKeywordEntities =
                userKeywordRepository.findAllByUserIdAndIsEnabled(userId, true)
                .orElseThrow(() -> new GovisException(ReturnCode.NO_KEYWORD));

        return userKeywordEntities.stream()
                .map(UserKeywordEntity::getKeyword)
                .collect(Collectors.toList());
    }
}
