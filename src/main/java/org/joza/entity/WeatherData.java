package org.joza.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class WeatherData {

    // fields for weather_data table

    private UUID id;

    private UUID locationId; // --> this acts as a foreign key from the Location class

    private LocalDate date;

    private double temperature;

    private double apparentTemperature; // --> the scientific term used by meteorologists
                                        //     for what is otherwise known as 'feels-like temperature'.
    private String weatherDescription;

    private double pressure;

    private double humidity;

    private double windSpeed;

    private String windDirection;

}