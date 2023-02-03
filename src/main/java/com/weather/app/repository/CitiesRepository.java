package com.weather.app.repository;

import com.weather.app.entity.CityEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CitiesRepository extends ReactiveMongoRepository<CityEntity, String> {

    Flux<CityEntity> findAllByIsActive(Boolean isActive);

    Mono<CityEntity> findByName(String name);
}
