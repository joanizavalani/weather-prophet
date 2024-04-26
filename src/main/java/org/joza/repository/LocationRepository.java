package org.joza.repository;

import org.joza.entity.Location;

import java.util.List;
import java.util.UUID;

public interface LocationRepository {

    // --> this interface has two classes to implement: One, will act as a service, which will connect ConsoleUI
    //     to this interface, the other will be a data access object which will connect to the database in MySQL.

    void addLocation(Location location);

    List<Location> viewAllLocations();

    List<Location> viewLocationsByCity(String cityName);

    void updateLocation(Location location);

    void deleteLocation(UUID locationId);

    Location getLocation(UUID locationId);



}
