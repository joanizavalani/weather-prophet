package org.joza.service;

import lombok.AllArgsConstructor;

import org.joza.entity.Location;
import org.joza.entity.WeatherData;
import org.joza.repository.LocationRepository;
import org.joza.repository.WeatherDataRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class WeatherDataService implements WeatherDataRepository {

    public final WeatherDataRepository weatherDataRepository;
    private LocationRepository locationRepository;


    @Override
    public void addWeatherData(WeatherData weatherData) {
        weatherDataRepository.addWeatherData(weatherData);
    }

    @Override
    public List<WeatherData> getWeatherDataByLocation(UUID locationId) {
        return weatherDataRepository.getWeatherDataByLocation(locationId);
    }

    // we reuse the getLocation method below from the location rep. which connects to the DAO and use it to verify if
    // it is a valid location in the downloadWeatherData() method

    private Location getLocation(UUID locationId){
        return locationRepository.getLocation(locationId);
    }

    public void downloadWeatherData(UUID locationId, LocalDate date){
        // JSON connection and API need to be established first
    }

}
