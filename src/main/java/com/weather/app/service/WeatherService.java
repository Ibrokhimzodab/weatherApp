package com.weather.app.service;

import com.weather.app.entity.CityEntity;
import com.weather.app.entity.SubscriptionEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.entity.WeatherEntity;
import com.weather.app.exeptions.NotFoundRequestException;
import com.weather.app.models.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WeatherService extends UserDetailsService {

    //////////       users        ///////////

    Mono<UserEntity> registerUser(RegisterUserModel registerUserModel);

    UserEntity getUserByEmail(String email) throws NotFoundRequestException;

    LoginResponseModel refreshToken(String refreshToken);

    Flux<CityEntity> getAllCities();

    Mono<SubscriptionEntity> subscribeToCity(String userId, String cityId);

    Mono<SubscriptionEntity> cancelSubscription(String userId, String cityId);

    Flux<WeatherEntity> getWeatherFromSubscribedCities(String userId);

    ///////////         root        /////////////

    Flux<UsersTableView> getAllUsers();

    Mono<UserDetailsModel> getUserDetails(String userId);

    Mono<UserEntity> editUser(String userId, EditUserModel model);

    Mono<CityDetailsModel> getCityWithDetails(String cityId);

    Mono<CityEntity> addNewCity(AddCityModel model);

    Mono<CityEntity> editCity(String cityId, EditCityModel model);

    Mono<CityEntity> disableCity(String cityId);

    Mono<WeatherEntity> addWeather(String cityId, SetWeatherModel model);

    Mono<WeatherEntity> editWeather(String cityId, SetWeatherModel model);
}
