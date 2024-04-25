package org.joza.connection;

import org.joza.entity.Location;
import org.joza.repository.LocationRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationDAO implements LocationRepository {

    // this class takes the inputted variables from the console user interface and connects the to the database

    @Override
    public void addLocation(Location location) {

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "INSERT INTO locations(id, city_name, region_name, country_name)" +
                    "values (?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, location.getId().toString());
            statement.setString(2, location.getCityName());
            statement.setString(3, location.getRegionName());
            statement.setString(4, location.getCountryName());

            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Location> viewAllLocations() {

        List<Location> locations = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "SELECT * FROM locations";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // loop basically adds each location to the arraylist one by one

            while (resultSet.next()){

                Location location = new Location();

                location.setId(UUID.fromString(resultSet.getString("id")));
                location.setCityName(resultSet.getString("city_name"));
                location.setRegionName(resultSet.getString("region_name"));
                location.setCountryName(resultSet.getString("country_name"));

                locations.add(location);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return locations;
    }

    @Override
    public void updateLocation(Location location) {

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "UPDATE locations SET city_name = ?, region_name = ?, country_name = ? " +
                    "WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, location.getCityName());
            statement.setString(2, location.getRegionName());
            statement.setString(3, location.getCountryName());
            statement.setString(4, location.getId().toString());

            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteLocation(UUID locationId) {

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "DELETE FROM locations WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, locationId.toString());

            statement.executeUpdate();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Location getLocation(UUID locationId) {

        Location location = null;

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "SELECT * FROM locations WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, locationId.toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                location = new Location();
                location.setId(UUID.fromString(resultSet.getString("id")));
                location.setCityName(resultSet.getString("city_name"));
                location.setRegionName(resultSet.getString("region_name"));
                location.setCountryName(resultSet.getString("country_name"));

            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return location;
    }

    @Override
    public List<Location> getLocationByCity(String cityName) {

        // works similar to viewAllLocations, but instead shows all the locations which share a city name

        List<Location> locations = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(
                DatabaseConstants.DB_URL + DatabaseConstants.DB_NAME,
                DatabaseConstants.DB_USERNAME, DatabaseConstants.DB_PASSWORD)){

            String query = "SELECT * FROM locations WHERE city_name = ?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, cityName);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                Location location = new Location();

                location.setId(UUID.fromString(resultSet.getString("id")));
                location.setCityName(resultSet.getString("city_name"));
                location.setRegionName(resultSet.getString("region_name"));
                location.setCountryName(resultSet.getString("country_name"));

                locations.add(location);
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return locations;
    }
}
