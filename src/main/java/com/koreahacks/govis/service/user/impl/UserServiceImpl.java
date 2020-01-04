package com.koreahacks.govis.service.user.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.User;
import com.koreahacks.govis.entity.user.UserEntity;
import com.koreahacks.govis.entity.user.UserKeywordEntity;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.repository.user.UserKeywordRepository;
import com.koreahacks.govis.repository.user.UserRepository;
import com.koreahacks.govis.service.auth.AuthService;
import com.koreahacks.govis.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Value("${google.client.android}")
    private String googleClientAndroid;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserKeywordRepository userKeywordRepository;
    @Autowired
    private AuthService authService;

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
                .accessToken(authService.issueAccessToken(userEntity.get().getUserId()))
                .userInfo(modelMapper.map(userEntity, User.Info.class))
                .build();
    }

    @Override
    public Optional<UserEntity> signUp(User.SignUp signUp) throws Exception {

        UserEntity userEntity = modelMapper.map(signUp, UserEntity.class);

        return Optional.ofNullable(userRepository.save(userEntity));
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

    @Override
    public void addUserKeywords(int userId, List<String> userKeywords) throws Exception {

        List<UserKeywordEntity> userKeywordEntities = new ArrayList<>();

        for (String userKeyword : userKeywords) {
            userKeywordEntities.add(
                    UserKeywordEntity.builder()
                    .userId(userId)
                    .keyword(userKeyword)
                    .isEnabled(true)
                    .build()
            );
        }

        System.out.println(userKeywordEntities.get(0).getKeyword());

        userKeywordRepository.saveAll(userKeywordEntities);
    }
}
