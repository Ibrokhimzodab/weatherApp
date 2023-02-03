package com.weather.app.bootstrap;

import com.weather.app.entity.*;
import com.weather.app.repository.CitiesRepository;
import com.weather.app.repository.SubscriptionsRepository;
import com.weather.app.repository.UsersRepository;
import com.weather.app.repository.WeatherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WeatherLoader implements CommandLineRunner {

    private final CitiesRepository citiesRepository;
    private final UsersRepository usersRepository;
    private final WeatherRepository weatherRepository;
    private final SubscriptionsRepository subscriptionsRepository;

    public WeatherLoader(CitiesRepository citiesRepository, UsersRepository usersRepository, WeatherRepository weatherRepository, SubscriptionsRepository subscriptionsRepository) {
        this.citiesRepository = citiesRepository;
        this.usersRepository = usersRepository;
        this.weatherRepository = weatherRepository;
        this.subscriptionsRepository = subscriptionsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadDbObjects();
    }

    private synchronized void loadDbObjects() {
        usersRepository.deleteAll().block();
        citiesRepository.deleteAll().block();
        weatherRepository.deleteAll().block();
        weatherRepository.deleteAll().block();
        usersRepository.save(new UserEntity("root@gmail.com", "First Name", "Last Name",
                UserRole.ROOT, "$2a$10$Vnny.S/eDrfKC1EDAdRUEeQXOvky1HNc5RqOpncUaYZBQMwjSz5RG")).block();

        var cityId = Objects.requireNonNull(citiesRepository.save(new CityEntity("Tashkent")).block()).getId();

        var cityId2 = Objects.requireNonNull(citiesRepository.save(new CityEntity("Samarkand")).block()).getId();

        var cityId3 = Objects.requireNonNull(citiesRepository.save(new CityEntity("New York")).block()).getId();

        var userId = Objects.requireNonNull(usersRepository.save(new UserEntity("user@gmail.com",
                "First Name", "Last Name", UserRole.USER,
                "$2a$10$Vnny.S/eDrfKC1EDAdRUEeQXOvky1HNc5RqOpncUaYZBQMwjSz5RG")).block()).getId();
        weatherRepository.save(new WeatherEntity(cityId, 10, WeatherType.SUNNY)).block();

        weatherRepository.save(new WeatherEntity(cityId2, 20, WeatherType.CLOUDY)).block();

        weatherRepository.save(new WeatherEntity(cityId3, -5, WeatherType.STORMY)).block();

        subscriptionsRepository.save(new SubscriptionEntity(userId, cityId)).block();

        subscriptionsRepository.save(new SubscriptionEntity(userId, cityId2)).block();

        subscriptionsRepository.save(new SubscriptionEntity(userId, cityId3)).block();

    }
}
