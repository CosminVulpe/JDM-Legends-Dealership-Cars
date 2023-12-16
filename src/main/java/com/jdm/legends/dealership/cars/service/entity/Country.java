package com.jdm.legends.dealership.cars.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String countryCode;
    private String countryName;
    private String isoNumeric;
    private String continent;
    private String continentName;
    private String capital;
    private String currencyCode;
    private double latitude;
    private double longitude;
}
