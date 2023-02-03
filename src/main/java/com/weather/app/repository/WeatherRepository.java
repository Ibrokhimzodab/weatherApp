package com.weather.app.repository;

import com.weather.app.entity.WeatherEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface WeatherRepository extends ReactiveMongoRepository<WeatherEntity, String> {

    Mono<WeatherEntity> findByCityId(String cityId);
}
