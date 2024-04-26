package org.joza.service;

import lombok.AllArgsConstructor;
import org.joza.entity.Location;
import org.joza.repository.LocationRepository;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor

public class LocationService implements LocationRepository {

    private final LocationRepository locationRepository; // --> this class links the inputs given from
                                                         //     console UI to the interface

    @Override
    public void addLocation(Location location) {

        locationRepository.addLocation(location);
    }

    @Override
    public List<Location> viewAllLocations() {

        return locationRepository.viewAllLocations();
    }

    @Override
    public List<Location> viewLocationsByCity(String cityName) {

        return locationRepository.viewLocationsByCity(cityName);
    }

    @Override
    public void updateLocation(Location location) {

        locationRepository.updateLocation(location);
    }

    @Override
    public void deleteLocation(UUID locationId) {

        locationRepository.deleteLocation(locationId);
    }

    @Override
    public Location getLocation(UUID locationId) {

        return locationRepository.getLocation(locationId);
    }

}
