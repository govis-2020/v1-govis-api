package com.koreahacks.govis.service.user;

import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.User;
import com.koreahacks.govis.entity.user.UserEntity;

import java.util.Optional;

public interface UserService {

    Login.LoginInfo login(String googleIdToken) throws Exception;
    Optional<UserEntity> signUp(User.SignUp signUp) throws Exception;
    String issueAccessToken(long userId) throws Exception;
}
