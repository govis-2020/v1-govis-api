package com.koreahacks.govis.repository.user;

import com.koreahacks.govis.entity.user.AccessTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessTokenRepository extends CrudRepository<AccessTokenEntity, Integer> {

    Optional<List<AccessTokenEntity>> findAllByUserIdAndIsEnabled(int userId, boolean isEnabled) throws Exception;
}
