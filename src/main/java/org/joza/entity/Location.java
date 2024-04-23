package org.joza.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@Data

public class Location {

    // fields for Locations table

    private UUID id;

    private String cityName;

    private String regionName;

    private String countryName;

}
