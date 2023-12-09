package com.jdm.legends.dealership.cars.service.parserXml;

import javax.xml.bind.annotation.XmlAttribute;

public class Status {
    private String message;

    public Status() {
    }

    public Status(String message) {
        this.message = message;
    }

    @XmlAttribute(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
