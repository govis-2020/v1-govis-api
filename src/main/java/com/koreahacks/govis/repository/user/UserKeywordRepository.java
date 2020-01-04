package com.koreahacks.govis.repository.user;

import com.koreahacks.govis.entity.user.UserKeywordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserKeywordRepository extends CrudRepository<UserKeywordEntity, Integer> {

    Optional<List<UserKeywordEntity>> findAllByUserIdAndIsEnabled(int userId, boolean isEnabled) throws Exception;
}
