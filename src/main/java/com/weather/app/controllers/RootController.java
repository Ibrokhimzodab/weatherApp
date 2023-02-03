package com.weather.app.controllers;


import com.weather.app.entity.CityEntity;
import com.weather.app.entity.UserEntity;
import com.weather.app.entity.WeatherEntity;
import com.weather.app.models.*;
import com.weather.app.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/root")
public class RootController {

    private final WeatherService weatherService;

    public RootController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/users")
    public Flux<ResponseEntity<UsersTableView>> getAllUsers(){
        return weatherService.getAllUsers().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/{userId}")
    public Mono<ResponseEntity<UserDetailsModel>> getUserDetails(@PathVariable String userId){
        return weatherService.getUserDetails(userId).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{userId}")
    public Mono<ResponseEntity<UserResponseModel>> editUser(@PathVariable String userId, @RequestBody @Valid EditUserModel model){
        return weatherService.editUser(userId, model).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("/cities")
    public Flux<ResponseEntity<CityEntity>> getAllCities(){
        return weatherService.getAllCities().map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/cities/{cityId}")
    public Mono<ResponseEntity<CityDetailsModel>> getCityDetails(@PathVariable String cityId){
        return weatherService.getCityWithDetails(cityId).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/cities")
    public Mono<ResponseEntity<CityEntity>> addCity(@RequestBody @Valid AddCityModel model){
        return weatherService.addNewCity(model).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping ("/cities/{cityId}")
    public Mono<ResponseEntity<CityEntity>> editCity(@PathVariable String cityId, @RequestBody @Valid EditCityModel model){
        return weatherService.editCity(cityId, model).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PatchMapping("/cities/{cityId}/disable")
    public Mono<ResponseEntity<Void>> disableCity(@PathVariable String cityId){
        return weatherService.disableCity(cityId).map( r -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping ("/cities/{cityId}/weather")
    public Mono<ResponseEntity<WeatherEntity>> addCityWeather(@PathVariable String cityId, @RequestBody @Valid SetWeatherModel model){
        return weatherService.addWeather(cityId, model).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping ("/cities/{cityId}/weather")
    public Mono<ResponseEntity<WeatherEntity>> editCityWeather(@PathVariable String cityId, @RequestBody @Valid SetWeatherModel model){
        return weatherService.editWeather(cityId, model).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
