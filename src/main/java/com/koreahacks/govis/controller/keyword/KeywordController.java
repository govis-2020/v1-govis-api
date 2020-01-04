package com.koreahacks.govis.controller.keyword;

import com.koreahacks.govis.dto.GovisDefaultResponse;
import com.koreahacks.govis.dto.Keyword;
import com.koreahacks.govis.dto.UserKeyword;
import com.koreahacks.govis.enums.ReturnCode;
import com.koreahacks.govis.exception.GovisException;
import com.koreahacks.govis.service.keyword.KeywordService;
import com.koreahacks.govis.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class KeywordController {

    @Autowired
    private UserService userService;
    @Autowired
    private KeywordService keywordService;

    @GetMapping("keywords")
    @ApiOperation(httpMethod = "GET", value = "현재 현행화되어있는 키워드 목록 조회")
    public Keyword.Response getKeywords(@RequestHeader("userId") String userId) throws Exception {

        try {
            if ("".equals(userId)) {
                throw new GovisException(ReturnCode.UNAUTHORIZED);
            }

            return new Keyword.Response(ReturnCode.SUCCESS, keywordService.getKeywords());
        } catch (GovisException e) {
            return new Keyword.Response(e.getCode(), e.getMessage());
        }
    }

    @PostMapping("keywords")
    @ApiOperation(httpMethod = "POST", value = "사용자가 지정한 관심있는 키워드 목록 등록")
    public GovisDefaultResponse addUserKeywords(@RequestHeader("userId") String userId,
                                                @RequestBody
                                                @Valid UserKeyword.Request userKeywordRequest) throws Exception {

        try {
            if ("".equals(userId)) {
                throw new GovisException(ReturnCode.UNAUTHORIZED);
            }

            userService.addUserKeywordIds(Integer.parseInt(userId), userKeywordRequest.getUserKeywordIds());

            return new GovisDefaultResponse(ReturnCode.SUCCESS);
        } catch (GovisException e) {
            return new GovisDefaultResponse(e.getCode(), e.getMessage());
        }
    }
}
