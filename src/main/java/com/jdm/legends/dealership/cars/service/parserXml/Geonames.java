package com.jdm.legends.dealership.cars.service.parserXml;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "geonames")
public class Geonames {
    private final List<CountryResponse> countries;

    public Geonames() {
        this.countries = new ArrayList<>();
    }

    @XmlElement(name = "country")
    public List<CountryResponse> getCountryList() {
        return countries;
    }
}
