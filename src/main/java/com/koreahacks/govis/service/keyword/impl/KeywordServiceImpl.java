package com.koreahacks.govis.service.keyword.impl;

import com.koreahacks.govis.entity.keyword.KeywordEntity;
import com.koreahacks.govis.repository.keyword.KeywordRepository;
import com.koreahacks.govis.service.keyword.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    public List<KeywordEntity> getKeywords() throws Exception {

        return keywordRepository.findAll();
    }
}
