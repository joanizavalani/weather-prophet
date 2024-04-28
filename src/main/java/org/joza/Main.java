package org.joza;

import org.joza.connection.DatabaseCreator;
import org.joza.connection.LocationDAO;
import org.joza.connection.WeatherDataDAO;
import org.joza.repository.LocationRepository;
import org.joza.repository.WeatherDataRepository;
import org.joza.service.LocationService;
import org.joza.service.WeatherDataService;

public class Main {

    public static void main(String[] args){

        // weatherData repository, service & DAO to be added next

        try {

            new DatabaseCreator(); // DB & tables created on MySQL

            // Console UI connects to the services, since it takes them as parameters.
            // Each service implements its respective repository and takes it as a parameter, and since the repository is
            // connected to the DAO through polymorphism, whatever value is set in the console UI, the DAO gets
            // and connects it to the database.

            LocationRepository locationRepository = new LocationDAO();
            LocationService locationService = new LocationService(locationRepository);

            WeatherDataRepository weatherDataRepository = new WeatherDataDAO();
            WeatherDataService weatherDataService = new WeatherDataService(weatherDataRepository, locationRepository);

            ConsoleUI consoleUI = new ConsoleUI(locationService, weatherDataService);
            consoleUI.runMenu();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}