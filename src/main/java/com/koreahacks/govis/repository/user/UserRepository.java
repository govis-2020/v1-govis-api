package com.koreahacks.govis.repository.user;

import com.koreahacks.govis.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findTopByEmailAndIsEnabled(String email, boolean isEnabled) throws Exception;
}
