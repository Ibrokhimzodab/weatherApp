package com.weather.app.maps;

import com.weather.app.entity.CityEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.models.UserSubscriptionsModel;
import com.weather.app.models.UsersTableView;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper()
public interface WeatherMapper {

    Flux<UsersTableView> mapUserEntityToUsersTableView(UserEntity userEntity);

    Mono<UserSubscriptionsModel> mapCityEntityToUserSubscriptionModel(CityEntity cityEntity);
}
