package com.koreahacks.govis.service.user.impl;

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
    public Login.Info login(String email, String userName) throws Exception {

        Optional<UserEntity> userEntity = userRepository.findTopByEmailAndIsEnabled(email, true);

        // 유저정보 없을 시 회원가입 처리
        if (!userEntity.isPresent()) {
            userEntity = signUp(User.SignUp.builder()
                    .email(email)
                    .userName(userName)
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
        userRepository.save(userEntity);

        return Optional.ofNullable(userEntity);
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
