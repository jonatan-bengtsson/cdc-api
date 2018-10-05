package com.tingcore.cdc.charging.model;

public class Location {
    private Address address;
    private GeoCoordinate geoCoordinate;


    public Location() {}

    public Location(Address address, GeoCoordinate geoCoordinate) {
        this.address = address;
        this.geoCoordinate = geoCoordinate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GeoCoordinate getGeoCoordinate() {
        return geoCoordinate;
    }

    public void setGeoCoordinate(GeoCoordinate geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address=" + address +
                ", geoCoordinate=" + geoCoordinate +
                '}';
    }
}

