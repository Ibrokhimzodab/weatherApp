package com.weather.app.service;

import com.weather.app.entity.RefreshTokenEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.exeptions.CustomGeneralException;
import com.weather.app.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshTokenEntity saveToken(UserEntity user, String token) {
        try {
            RefreshTokenEntity refresh = new RefreshTokenEntity();
            refresh.setToken(token);
            refresh.setUserId(user.getId());
            refresh.setCreatedAt(LocalDateTime.now());
            return refreshTokenRepository.save(refresh);
        } catch (Exception e) {
            throw new CustomGeneralException("Could not read refresh token record", "0000");
        }
    }

    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public Optional<RefreshTokenEntity> getToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void checkAndDeleteRefreshToken(UserEntity user){
        var entity = refreshTokenRepository.findByUserId(user.getId());
        entity.ifPresent(refreshTokenEntity -> deleteToken(refreshTokenEntity.getToken()));
    }
}

