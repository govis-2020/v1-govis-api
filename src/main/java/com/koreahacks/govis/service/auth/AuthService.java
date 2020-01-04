package com.koreahacks.govis.service.auth;

public interface AuthService {

    String issueAccessToken(int userId) throws Exception;
    String verifyAccessToken(String accessToken) throws Exception;
}
