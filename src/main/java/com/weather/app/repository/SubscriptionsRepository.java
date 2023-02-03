package com.weather.app.repository;

import com.weather.app.entity.SubscriptionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SubscriptionsRepository extends ReactiveMongoRepository<SubscriptionEntity, String> {

    Mono<SubscriptionEntity> findByUserIdAndCityId(String userId, String cityId);

    Flux<SubscriptionEntity> findAllByUserId(String userId);
}
