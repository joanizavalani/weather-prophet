package org.joza.service;

import lombok.AllArgsConstructor;

import org.jetbrains.annotations.Nullable;
import org.joza.entity.Location;
import org.joza.entity.WeatherData;
import org.joza.repository.LocationRepository;
import org.joza.repository.WeatherDataRepository;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor

public class WeatherDataService implements WeatherDataRepository {

    //fields
    private final WeatherDataRepository weatherDataRepository;

    private final LocationRepository locationRepository;

    @Override
    public void addWeatherData(WeatherData weatherData) {
        weatherDataRepository.addWeatherData(weatherData);
    }

    @Override
    public List<WeatherData> getWeatherDataByLocation(UUID locationId) {
        return weatherDataRepository.getWeatherDataByLocation(locationId);
    }

    public void downloadWeatherData(UUID locationId, LocalDate date){

        try {

            Location location = getLocation(locationId);
                if(location == null){
                    System.out.println("Location not found.");
                    System.out.println();
                    return;
                }

            JSONObject weatherDataJson = getWeatherDataJSON(location.getCityName());
                if (weatherDataJson == null) {
                    System.out.println("GET request not worked.");
                    return;
                }

            WeatherData weatherData = createWeatherData(locationId, date, weatherDataJson);
            addWeatherData(weatherData);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
        // JSON connection and API need to be established first
    }

    // we reuse the getLocation method below from the location rep. which connects to the DAO and use it to verify if
    // it is a valid location in the downloadWeatherData() method

    private Location getLocation(UUID locationId){
        return locationRepository.getLocation(locationId);
    }

    @Nullable
    private JSONObject getWeatherDataJSON(String cityName) throws IOException {

        String apiEndpoint = "http://api.openweathermap.org/data/2.5/weather";
        String apiKey = "8e3d492ebec0c41e1724e40229c4281d";

        URL url = new URL(STR."\{apiEndpoint}?q=\{cityName}&appid=\{apiKey}");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return new JSONObject(response.toString());
            }

        return null;
    }

    private WeatherData createWeatherData(UUID locationId, LocalDate date, JSONObject weatherDataJson) {
        WeatherData weatherData = new WeatherData();
        weatherData.setLocationId(locationId);
        weatherData.setId(UUID.randomUUID());
        weatherData.setDate(date);
        weatherData.setTemperature(weatherDataJson.getJSONObject("main").getDouble("temp"));
        weatherData.setApparentTemperature(weatherDataJson.getJSONObject("main").getDouble("feels_like"));
        weatherData.setPressure(weatherDataJson.getJSONObject("main").getDouble("pressure"));
        weatherData.setHumidity(weatherDataJson.getJSONObject("main").getDouble("humidity"));
        weatherData.setWindDirection(Double.toString(weatherDataJson.getJSONObject("wind").getDouble("deg")));
        weatherData.setWindSpeed(weatherDataJson.getJSONObject("wind").getDouble("speed"));
        weatherData.setWeatherDescription(weatherDataJson.getJSONArray("weather").getJSONObject(0).getString("description"));

        return weatherData;
    }






}
