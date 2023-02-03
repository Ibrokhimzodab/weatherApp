package com.weather.app.repository;

import com.weather.app.entity.UserEntity;
import com.weather.app.entity.UserRole;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UsersRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findAllByUserRole(UserRole role);
}
