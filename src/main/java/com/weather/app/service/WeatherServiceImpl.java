package com.weather.app.service;

import com.weather.app.entity.*;
import com.weather.app.exeptions.NotFoundRequestException;
import com.weather.app.exeptions.UnauthorizedRequestException;
import com.weather.app.maps.WeatherMapper;
import com.weather.app.models.*;
import com.weather.app.repository.CitiesRepository;
import com.weather.app.repository.SubscriptionsRepository;
import com.weather.app.repository.UsersRepository;
import com.weather.app.repository.WeatherRepository;
import com.weather.app.utils.Constants;
import com.weather.app.utils.JWTUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final CitiesRepository citiesRepository;
    private final UsersRepository usersRepository;
    private final WeatherRepository weatherRepository;
    private final SubscriptionsRepository subscriptionsRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final WeatherMapper weatherMapper;

    public WeatherServiceImpl(UsersRepository usersRepository, CitiesRepository citiesRepository, WeatherRepository weatherRepository, SubscriptionsRepository subscriptionsRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, WeatherMapper weatherMapper) {
        this.citiesRepository = citiesRepository;
        this.usersRepository = usersRepository;
        this.weatherRepository = weatherRepository;
        this.subscriptionsRepository = subscriptionsRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.weatherMapper = weatherMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email)
                .map(user -> User.builder()
                            .username(user.getEmail())
                            .password(user.getPassword())
                            .authorities(user.getUserRole().name())
                            .roles(user.getUserRole().name())
                            .build()).block();
    }

    @Override
    public Mono<UserEntity> registerUser(RegisterUserModel registerUserModel) {
        return usersRepository.findByEmail(registerUserModel.getEmail())
                .switchIfEmpty(usersRepository.save(new UserEntity(
                        registerUserModel.getEmail(),
                        registerUserModel.getFirstName(),
                        registerUserModel.getLastName(),
                        UserRole.USER,
                        passwordEncoder.encode(registerUserModel.getPassword())
                )));
    }

    @Override
    public UserEntity getUserByEmail(String email) throws NotFoundRequestException{
        return usersRepository.findByEmail(email).block();
    }

    @Override
    public LoginResponseModel refreshToken(String refreshToken) {
        var refreshRecord = refreshTokenService.getToken(refreshToken);
        if (refreshRecord.isEmpty()) {
            throw new UnauthorizedRequestException(
                    "Token is expired or invalid, please login again to take new refresh token", "0401"
            );
        }

        var claims = JWTUtils.getUserDetails(refreshToken, Constants.REFRESH_KEY);

        Optional<UserEntity> optUser = usersRepository.findByEmail(claims.getEmail()).blockOptional();
        if (optUser.isPresent()) {
            LoginResponseModel responseModel = new LoginResponseModel();
            String newAccess = JWTUtils.createToken(optUser.get(),
                    Constants.COMPANY_NAME,
                    Constants.ACCESS_KEY,
                    Constants.ACCESS_TTL);
            responseModel.setId(optUser.get().getId());
            responseModel.setEmail(optUser.get().getEmail());
            responseModel.setUserRole(optUser.get().getUserRole());
            responseModel.setRefreshToken(refreshToken);
            responseModel.setAccessToken(newAccess);
            return responseModel;
        } else {
            throw new UnauthorizedRequestException();
        }
    }

    @Override
    public Flux<CityEntity> getAllCities() {
        return citiesRepository.findAllByIsActive(true);
    }

    @Override
    public Mono<SubscriptionEntity> subscribeToCity(String userId, String cityId) {
        var subscription = new SubscriptionEntity();
        subscription.setCityId(cityId);
        subscription.setUserId(userId);
        return subscriptionsRepository.save(subscription);
    }

    @Override
    public Mono<SubscriptionEntity> cancelSubscription(String userId, String cityId) {
        return subscriptionsRepository.findByUserIdAndCityId(userId, cityId)
                .flatMap(subscription -> subscriptionsRepository.delete(subscription)
                .then(Mono.just(subscription)));
    }

    @Override
    public Flux<WeatherEntity> getWeatherFromSubscribedCities(String userId) {
        return subscriptionsRepository.findAllByUserId(userId)
                .flatMap(subscription -> weatherRepository.findAll()
                        .filter(value -> value.getCityId().equals(subscription.getCityId())));
    }

    ///////////        root       /////////////

    @Override
    public Flux<UsersTableView> getAllUsers() {
        return usersRepository.findAll()
                .flatMap(weatherMapper::mapUserEntityToUsersTableView);
    }

    @Override
    public Mono<UserDetailsModel> getUserDetails(String userId) {
        return usersRepository.findById(userId)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(user -> {
                    var details = new UserDetailsModel();
                    details.setId(user.getId());
                    details.setEmail(user.getEmail());
                    details.setFirstName(user.getFirstName());
                    details.setLastName(user.getLastName());
                    details.setIsActive(user.getIsActive());
                    details.setUserSubscriptions(subscriptionsRepository.findAllByUserId(user.getId())
                            .flatMap(subscription -> citiesRepository.findById(subscription.getCityId())
                                    .flatMap(weatherMapper::mapCityEntityToUserSubscriptionModel))
                            .toStream().collect(Collectors.toList()));
                    return Mono.just(details);
                });
    }

    @Override
    public Mono<UserEntity> editUser(String userId, EditUserModel model) {
        return usersRepository.findById(userId).flatMap(user -> {
            user.setFirstName(model.getFirstName());
            user.setLastName(model.getLastName());
            user.setIsActive(model.getIsActive());
            return usersRepository.save(user);
        });
    }

    @Override
    public Mono<CityDetailsModel> getCityWithDetails(String cityId) {
        var city = citiesRepository.findById(cityId).subscribeOn(Schedulers.boundedElastic());
        var weather = weatherRepository.findByCityId(cityId).subscribeOn(Schedulers.boundedElastic());
        return Mono.zip(city, weather, cityAndWeatherBiFunction);
    }

    private final BiFunction<CityEntity, WeatherEntity, CityDetailsModel> cityAndWeatherBiFunction =
            (city, weather) -> new CityDetailsModel(
                    city.getId(),
                    city.getName(),
                    weather.getTemp(),
                    weather.getWeatherType(),
                    weather.getUpdatedAt());

    @Override
    public Mono<CityEntity> addNewCity(AddCityModel model) {
        return citiesRepository.save(new CityEntity(model.getName()));
    }

    @Override
    public Mono<CityEntity> editCity(String cityId, EditCityModel model) {
        return citiesRepository.findById(cityId)
                .flatMap(city -> {
                    city.setName(model.getName());
                    city.setIsActive(model.getIsActive());
                    return citiesRepository.save(city);
                });
    }

    @Override
    public Mono<CityEntity> disableCity(String cityId) {
        return citiesRepository.findById(cityId)
                .flatMap(city -> {
                    city.setIsActive(false);
                    return citiesRepository.save(city);
                });
    }

    @Override
    public Mono<WeatherEntity> addWeather(String cityId, SetWeatherModel model) {
        return citiesRepository.findById(cityId)
                .flatMap(cityEntity -> weatherRepository.save(new WeatherEntity(
                        cityEntity.getId(),
                        model.getTemp(),
                        model.getWeatherType()
                )));
    }

    @Override
    public Mono<WeatherEntity> editWeather(String cityId, SetWeatherModel model) {
        return citiesRepository.findById(cityId)
                .flatMap(cityEntity -> weatherRepository.findByCityId(cityId)
                        .flatMap(weather -> {
                            weather.setTemp(model.getTemp());
                            weather.setWeatherType(model.getWeatherType());
                            weather.setUpdatedAt(LocalDateTime.now());
                            return weatherRepository.save(weather);
                        }));
    }
}
