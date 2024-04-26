package org.joza.repository;

import org.joza.entity.WeatherData;

import java.util.List;
import java.util.UUID;

public interface WeatherDataRepository {

    void addWeatherData(WeatherData weatherData);

    List<WeatherData> getWeatherDataByLocation(UUID locationId);

}
