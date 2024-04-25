package org.joza;

import org.joza.connection.DatabaseCreator;
import org.joza.connection.LocationDAO;
import org.joza.repository.LocationRepository;
import org.joza.service.LocationService;

public class Main {

    public static void main(String[] args) {

        // weatherData repository, service & DAO to be added next

        try {

            new DatabaseCreator(); // DB & tables created on MySQL

            // Console UI connects to the service, since it takes it as a parameter.
            // The service implements the repository and takes it as a parameter, and since the repository is
            // connected to the DAO through polymorphism, whatever value is set in the console UI, the DAO gets
            // and connects it to the database.

            LocationRepository locationRepository = new LocationDAO();
            LocationService locationService = new LocationService(locationRepository);

            ConsoleUI consoleUI = new ConsoleUI(locationService);

            consoleUI.runMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}