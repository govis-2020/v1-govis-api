package com.koreahacks.govis.service.user;

import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.User;
import com.koreahacks.govis.entity.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Login.Info login(String googleIdToken) throws Exception;
    Optional<UserEntity> signUp(User.SignUp signUp) throws Exception;
    String issueAccessToken(int userId) throws Exception;
    List<String> getUserKeywords(int userId) throws Exception;
    void addUserKeywords(int userId, List<String> userKeywords) throws Exception;
}
