package com.koreahacks.govis.controller.user;

import com.koreahacks.govis.dto.Login;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return new Login.Response(ReturnCode.SUCCESS, userService.login(loginRequest.getEmail(),
                    loginRequest.getUserName()));
        } catch (GovisException e) {
            return new Login.Response(e.getCode(), e.getMessage());
        }
    }
}
