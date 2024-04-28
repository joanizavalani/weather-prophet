package org.joza.connection;

import org.joza.entity.WeatherData;
import org.joza.repository.WeatherDataRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WeatherDataDAO implements WeatherDataRepository {

    @Override
    public void addWeatherData(WeatherData weatherData) {

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "INSERT INTO weather_data" +
                    "(id, locations_id, date, temperature, apparent_temperature, weather_description," +
                    "pressure, humidity, wind_speed, wind_direction)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, weatherData.getId().toString());
            statement.setString(2, weatherData.getLocationId().toString());
            statement.setDate(3, Date.valueOf(weatherData.getDate().toString()));
            statement.setDouble(4, weatherData.getTemperature());
            statement.setDouble(5, weatherData.getApparentTemperature());
            statement.setString(6, weatherData.getWeatherDescription());
            statement.setDouble(7, weatherData.getPressure());
            statement.setDouble(8, weatherData.getHumidity());
            statement.setDouble(9, weatherData.getWindSpeed());
            statement.setString(10, weatherData.getWindDirection());

            statement.executeUpdate();

            // since JSON and API haven't been added, for the time being, this will produce an error
            // because there's no variables to get (weatherData setters haven't been used in any way,
            // so there's no value for the getters to GET).

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeatherData> getWeatherDataByLocation(UUID locationId) {
        List<WeatherData> weatherDataList = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "SELECT * FROM weather_data WHERE locations_id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, locationId.toString());

            ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()){

                    WeatherData weatherData = new WeatherData();

                    weatherData.setId(UUID.fromString(resultSet.getString("id")));
                    weatherData.setLocationId(UUID.fromString(resultSet.getString("locations_id")));
                    weatherData.setDate(resultSet.getDate("date").toLocalDate());
                    weatherData.setTemperature(resultSet.getDouble("temperature"));
                    weatherData.setApparentTemperature(resultSet.getDouble("apparent_temperature"));
                    weatherData.setWeatherDescription(resultSet.getString("weather_description"));
                    weatherData.setPressure(resultSet.getDouble("pressure"));
                    weatherData.setHumidity(resultSet.getDouble("humidity"));
                    weatherData.setWindSpeed(resultSet.getDouble("wind_speed"));
                    weatherData.setWindDirection(resultSet.getString("wind_direction"));

                    weatherDataList.add(weatherData);
                }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return weatherDataList;
    }

}
