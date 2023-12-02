package com.jdm.legends.dealership.cars.service.entity;

import com.jdm.legends.dealership.cars.service.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetName;
    private String cityName;
    private String postalCode;
    private String countryName;
    private String region;

    private AddressType addressType;
}
