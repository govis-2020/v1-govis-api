package com.koreahacks.govis.service.user;

import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.User;
import com.koreahacks.govis.entity.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Login.Info login(String email, String userName) throws Exception;
    Optional<UserEntity> signUp(User.SignUp signUp) throws Exception;
    void addUserKeywordIds(int userId, List<Integer> userKeywordIds) throws Exception;
}
