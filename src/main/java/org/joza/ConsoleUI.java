package org.joza;

import org.joza.entity.Location;
import org.joza.entity.WeatherData;
import org.joza.service.LocationService;
import org.joza.service.WeatherDataService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleUI {

    // this class has all the methods that will appear on the console user interface
    // class takes parameters below to link all of its methods to the service

    private final LocationService locationService;

    private final WeatherDataService weatherDataService;

    private final Scanner scanner;

    // constructor

    public ConsoleUI(LocationService locationService, WeatherDataService weatherDataService){
        this.locationService = locationService;
        this.scanner = new Scanner(System.in);
        this.weatherDataService = weatherDataService;
    }

    // method that runs the console interface, connects to every method in this class

    public void runMenu(){

        boolean appIsRunning = true;

        titleMenu();

        while(appIsRunning){

            chooseMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addLocation();
                    break;

                case 2:
                    viewAllLocations();
                    break;

                case 3:
                    downloadWeatherData();
                    break;

                case 4:
                    viewWeatherData();
                    break;

                case 5:
                    searchLocation();
                    break;

                case 6:
                    updateLocation();
                    break;

                case 7:
                    deleteLocation();
                    break;

                case 8:
                    appIsRunning = false;
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Perhaps I wasn't clear... Please choose a number from 1 - 7.");
                    scanner.nextInt();
                    break;

            }
        }
    }

    // first method called, presents the program

    private void titleMenu(){

        System.out.println();
        System.out.println("""
                ╭── ⋅ ⋅ ── ~ ── ⋅ ⋅ ──╮
                                
                ~~ WEATHER PROPHET ~~
                                
                ╰── ⋅ ⋅ ── ~ ── ⋅ ⋅ ──╯
                """);

        System.out.println("Hi, you've entered the Weather Prophet's shrine of meteorology.");
        System.out.println("I can accurately guess the weather of any real-life location!");

        System.out.println();

        System.out.println("Press ENTER to continue. :)");

        scanner.nextLine();
    }

    private void chooseMenu(){

        System.out.println("...How many I be of use to you?");

        System.out.println();

        System.out.println("1 --> add a new location...");
        System.out.println("2 --> view all locations...");
        System.out.println("3 --> download weather data...");
        System.out.println("4 --> read weather prophecy (view weather data)...");
        System.out.println("5 --> search a location...");
        System.out.println("6 --> update a location...");
        System.out.println("7 --> delete a location...");
        System.out.println("8 --> exit...");

        System.out.println();

        System.out.println("Choose your option:");
    }

    // option 1
    private void addLocation(){

        System.out.println();
        System.out.println("~~~ NEW LOCATION ~~~");

        UUID id = UUID.randomUUID(); // --> randomly generating a location id

        System.out.println("Generated location ID: "+ id);
        System.out.println();

        System.out.println("Enter a city name:");
        String cityName = scanner.nextLine();

            while (cityName.isEmpty()){
                System.out.println("City name cannot be empty. Please enter a valid city name:");
                cityName = scanner.nextLine();
            }

        System.out.println("Enter a region name (optional):");
        String regionName = scanner.nextLine();

        System.out.println("Enter a country name:");
        String countryName = scanner.nextLine();

            while (countryName.isEmpty()){
                System.out.println("Country name cannot be empty. Please enter a valid country name:");
                countryName = scanner.nextLine();
            }

        // connecting method's variables with Location entity fields

        Location location = new Location();

        location.setId(id);
        location.setCityName(cityName);
        location.setRegionName(regionName);
        location.setCountryName(countryName);

        locationService.addLocation(location); // --> connected values with service

        System.out.println();
        System.out.println("Location was added to the database.");
        System.out.println();

    }

    // option 2
    private void viewAllLocations(){

        System.out.println();
        System.out.println("~~~ ALL LOCATIONS ~~~");

        System.out.println();

        for(Location location : locationService.viewAllLocations()){

            if(location.getRegionName().isEmpty()){  // --> check if there's no region name in a certain location,
                location.setRegionName("- ");        //     and if there isn't it gets replaced with a hyphen (-)
            }

            System.out.println(location.getId() + " --> [" + location.getCityName() + ", "
                    + location.getRegionName() +", "+ location.getCountryName()+ "]");
        }

        System.out.println();
        System.out.println("These are all the locations saved in your database.");
        System.out.println();

    }

    // option 3
    private void downloadWeatherData(){

        System.out.println();
        System.out.println("~~~ DOWNLOAD WEATHER DATA FROM A LOCATION ~~~");
        System.out.println();

        System.out.println("Please insert the ID of the location you want to download weather data from:");
        UUID locationId = UUID.fromString(scanner.nextLine());

        System.out.println("Please insert the desired date (yyyy-mm-dd) for your weather forecast:");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        weatherDataService.downloadWeatherData(locationId, date);

        System.out.println();
        System.out.println("Weather data was successfully downloaded.");
        System.out.println();
    }

    // option 4
    private void viewWeatherData(){

        System.out.println();
        System.out.println("~~~ VIEW WEATHER FORECAST FOR YOUR LOCATION ~~~");
        System.out.println();

        System.out.println("Please insert the ID of the location you want to view forecast of:");
        UUID locationId = UUID.fromString(scanner.nextLine());

        System.out.println();
        System.out.println("Here's your weather data:");
        System.out.println();

        for (WeatherData weatherData : weatherDataService.getWeatherDataByLocation(locationId)) {
            System.out.println("Date: "+ weatherData.getDate());
            System.out.println("Temperature: "+ weatherData.getTemperature());
            System.out.println("Feels Like: "+ weatherData.getApparentTemperature());
            System.out.println("Weather Description: "+ weatherData.getWeatherDescription());
            System.out.println("Pressure: "+ weatherData.getPressure());
            System.out.println("Humidity: "+ weatherData.getHumidity());
            System.out.println("Wind Direction: "+ weatherData.getWindDirection());
            System.out.println("Wind Speed: "+ weatherData.getWindSpeed());

            System.out.println();
        }
    }

    // option 5
    private void searchLocation(){

        System.out.println();
        System.out.println("~~~ SEARCH A LOCATION ~~~");
        System.out.println();

        System.out.println("Please insert the city name of the location you want to find:");
        String cityName = scanner.nextLine();

        List<Location> locationsSearched = locationService.viewLocationsByCity(cityName);

            if(locationsSearched.isEmpty()){
                    System.out.println("No locations were found with the city name you entered.");
                    System.out.println();
                    return;
            }

        for(Location location : locationsSearched){

            if(location.getRegionName().isEmpty()){  // --> check if there's no region name in a certain location,
                location.setRegionName("- ");        //     and if there isn't it gets replaced with a hyphen (-)
            }

            System.out.println(location.getId() + " --> [" + location.getCityName() + ", "
                    + location.getRegionName() +", "+ location.getCountryName()+ "]");

        }

        System.out.println();
        System.out.println("There are the locations corresponding with the city name you've entered.");
        System.out.println();
    }

    // option 6
    private void updateLocation(){

        System.out.println();
        System.out.println("~~~ UPDATE A LOCATION ~~~");
        System.out.println();

        System.out.println("Please insert the ID of the location you want to alter:");
        UUID id = UUID.fromString(scanner.nextLine());

        Location location = locationService.getLocation(id); // --> (polymorphism) this connects with the DAO
                                                             //     to check if it matches a location
            if(location == null){
                System.out.println("No location was found with your current ID.");
                System.out.println();
                return;
            }

        System.out.println("Alter city name:");
        String cityName = scanner.nextLine();

            while (cityName.isEmpty()){
                System.out.println("City name cannot be empty. Please enter a valid city name:");
                cityName = scanner.nextLine();
            }

        System.out.println("Alter region name (optional):");
        String regionName = scanner.nextLine();

        System.out.println("Alter country name:");
        String countryName = scanner.nextLine();

            while (countryName.isEmpty()){
                System.out.println("Country name cannot be empty. Please enter a valid country name:");
                countryName = scanner.nextLine();
            }

        // connecting method's variables with Location entity fields and service

        location.setId(id);
        location.setCityName(cityName);
        location.setRegionName(regionName);
        location.setCountryName(countryName);

        locationService.updateLocation(location);

        System.out.println();
        System.out.println("Location was successfully updated.");
        System.out.println();
    }

    // option 7
    private void deleteLocation(){

        System.out.println();
        System.out.println("~~~ DELETE A LOCATION ~~~");
        System.out.println();

        System.out.println("Please insert the ID of the location you want to delete:");
        UUID id = UUID.fromString(scanner.nextLine());

        Location location = locationService.getLocation(id); // --> (polymorphism) this connects with the DAO
                                                             //     to check if it matches a location
        if(location == null){
            System.out.println("No location was found with your current ID.");
            System.out.println();
            return;
        }

        locationService.deleteLocation(id);

        System.out.println();
        System.out.println("Location was successfully deleted.");
        System.out.println();

    }
}
