package com.koreahacks.govis.repository.keyword;

import com.koreahacks.govis.entity.keyword.KeywordEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends CrudRepository<KeywordEntity, Integer> {

    @Query("SELECT k FROM KeywordEntity k")
    List<KeywordEntity> findAll();
}
