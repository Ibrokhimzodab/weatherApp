package com.weather.app.bootstrap;

import com.weather.app.entity.UserEntity;
import com.weather.app.entity.UserRole;
import com.weather.app.repository.CitiesRepository;
import com.weather.app.repository.SubscriptionsRepository;
import com.weather.app.repository.UsersRepository;
import com.weather.app.repository.WeatherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        usersRepository.save(new UserEntity("root@gmail.com", "First Name", "Last Name",
                UserRole.ROOT, "$2a$10$Vnny.S/eDrfKC1EDAdRUEeQXOvky1HNc5RqOpncUaYZBQMwjSz5RG")).block();
    }
}
