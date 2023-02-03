package com.weather.app.controllers;

import com.weather.app.entity.CityEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.entity.WeatherEntity;
import com.weather.app.models.LoginResponseModel;
import com.weather.app.models.RegisterUserModel;
import com.weather.app.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class UserController {

    private final WeatherService weatherService;

    public UserController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<UserEntity>> registerUser(@RequestBody @Valid RegisterUserModel registerUserModel){
        return weatherService.registerUser(registerUserModel).map(ResponseEntity::ok);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponseModel> refreshToken(@RequestHeader("refreshToken") String refreshToken){
        LoginResponseModel responseModel = weatherService.refreshToken(refreshToken);
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/user/cities")
    public Flux<ResponseEntity<CityEntity>> getAllCities(){
        return weatherService.getAllCities().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}/subscribe/{cityId}")
    public Mono<ResponseEntity<Void>> subscribeToCity(@PathVariable String userId, @PathVariable String cityId){
        return weatherService.subscribeToCity(userId, cityId).map( r -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}/unsubscribe/{cityId}")
    public Mono<ResponseEntity<Void>> cancelSubscriptionToCity(@PathVariable String userId, @PathVariable String cityId){
        return weatherService.cancelSubscription(userId, cityId).map( r -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/user/{userId}/weather")
    public Flux<ResponseEntity<WeatherEntity>> getWeatherFromSubscribedCities(@PathVariable String userId){
        return weatherService.getWeatherFromSubscribedCities(userId).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
