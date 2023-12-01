package com.jdm.legends.dealership.cars.service.parserXml;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "geonames")
public class Geonames {
    private final List<CountryDTO> countries;

    public Geonames() {
        this.countries = new ArrayList<>();
    }

    @XmlElement(name = "country")
    public List<CountryDTO> getCountryList() {
        return countries;
    }
}
