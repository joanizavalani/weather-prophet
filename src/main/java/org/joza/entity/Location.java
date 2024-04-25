package org.joza.entity;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Location {

    // fields for Locations table

    private UUID id;

    private String cityName;

    private String regionName;

    private String countryName;

}
