package com.weather.app.repository;

import com.weather.app.entity.RefreshTokenEntity;
import com.weather.app.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

    void deleteByToken(String token);

    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByUserId(String userId);
}
