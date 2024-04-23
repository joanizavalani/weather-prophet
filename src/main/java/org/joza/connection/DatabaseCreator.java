package org.joza.connection;

import java.sql.*;

public class DatabaseCreator {

    // --> methods are made private so that they can only be accessed
    //     from the constructor in the main method

    public DatabaseCreator(){

        createDatabaseIfNotExists();
        createLocationsTableIfNotExists();
        createWeatherDataTableIfNotExists();
    }

    private void createDatabaseIfNotExists() {

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL, DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            Statement statement = connection.createStatement();
            String query = String.format("CREATE DATABASE IF NOT EXISTS %s", DatabaseConstants.DB_NAME);
            statement.executeUpdate(query);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void createLocationsTableIfNotExists(){

        try(Connection connection = DriverManager.getConnection(
            DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "CREATE TABLE IF NOT EXISTS locations(" +
                    "id VARCHAR(36) PRIMARY KEY," +
                    "city_name VARCHAR(255)," +
                    "region_name VARCHAR(255)," +
                    "country_name VARCHAR(255)" +
                    ")";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void createWeatherDataTableIfNotExists(){

        try(Connection connection = DriverManager.getConnection(
            DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "CREATE TABLE IF NOT EXISTS weather_data(" +
                    "id VARCHAR(36) PRIMARY KEY," +
                    "locations_id VARCHAR(36)," +
                    "temperature DOUBLE," +
                    "apparent_temperature DOUBLE," +
                    "weather_description VARCHAR(255)," +
                    "pressure DOUBLE," +
                    "humidity DOUBLE," +
                    "wind_speed DOUBLE," +
                    "wind_direction VARCHAR(255)" +
                    ")";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}