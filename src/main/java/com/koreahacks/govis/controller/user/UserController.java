package com.koreahacks.govis.controller.user;

import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.dto.UserKeyword;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ApiOperation(httpMethod = "POST", value = "로그인")
    public Login.Response login(@RequestBody @Valid Login.Request loginRequest) throws Exception {

        try {
            return new Login.Response(ReturnCode.SUCCESS, userService.login(loginRequest.getGoogleIdToken()));
        } catch (GovisException e) {
            return new Login.Response(e.getCode(), e.getMessage());
        }
    }

    @GetMapping("keywords")
    @ApiOperation(httpMethod = "GET", value = "사용자가 지정한 관심있는 키워드 목록 조회")
    public UserKeyword.Response getUserKeywords(@RequestHeader("userId") int userId) throws Exception {

        try {
            return new UserKeyword.Response(ReturnCode.SUCCESS, userService.getUserKeywords(userId));
        } catch (GovisException e) {
            return new UserKeyword.Response(e.getCode(), e.getMessage());
        }
    }
}
