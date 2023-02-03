package com.weather.app.maps;

import com.weather.app.entity.CityEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.entity.WeatherEntity;
import com.weather.app.models.CityDetailsModel;
import com.weather.app.models.UserDetailsModel;
import com.weather.app.models.UserSubscriptionsModel;
import com.weather.app.models.UsersTableView;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.processing.Generated;
import java.util.List;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class WeatherMapperImpl implements WeatherMapper {

    @Override
    public Flux<UsersTableView> mapUserEntityToUsersTableView(UserEntity userEntity) {
        var user = new UsersTableView();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setIsActive(userEntity.getIsActive());
        return Flux.just(user);
    }

    @Override
    public Mono<UserSubscriptionsModel> mapCityEntityToUserSubscriptionModel(CityEntity cityEntity) {
        var subscription = new UserSubscriptionsModel();
        subscription.setCityId(cityEntity.getId());
        subscription.setCityName(cityEntity.getName());
        return Mono.just(subscription);
    }
}
