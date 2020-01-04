package com.koreahacks.govis.service.keyword;

import com.koreahacks.govis.entity.keyword.KeywordEntity;

import java.util.List;

public interface KeywordService {

    List<KeywordEntity> getKeywords() throws Exception;
}
