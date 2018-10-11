package com.tingcore.cdc.charging.model;

import java.util.Optional;

public class Address {

    private String city;
    private String countryIsoCode;
    private String postalCode;
    private String region;
    private String regionIsoCode;
    private String street;
    private String number;
    private String floor;
    private String section;

    public Address() {}

    public Address(String city, String countryIsoCode, String postalCode, String region, String regionIsoCode, String street, String number, String floor, String section) {
        this.city = city;
        this.countryIsoCode = countryIsoCode;
        this.postalCode = postalCode;
        this.region = region;
        this.regionIsoCode = regionIsoCode;
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.section = section;
    }


    public Optional<String> getCity() {
        return Optional.ofNullable(city);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public Optional<String> getPostalCode() {
        return Optional.ofNullable(postalCode);
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Optional<String> getRegion() {
        return Optional.ofNullable(region);
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Optional<String> getRegionIsoCode() {
        return Optional.ofNullable(regionIsoCode);
    }

    public void setRegionIsoCode(String regionIsoCode) {
        this.regionIsoCode = regionIsoCode;
    }

    public Optional<String> getStreet() {
        return Optional.ofNullable(street);
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Optional<String> getFloor() {
        return Optional.ofNullable(floor);
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Optional<String> getSection() {
        return Optional.ofNullable(section);
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", countryIsoCode='" + countryIsoCode + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", region='" + region + '\'' +
                ", regionIsoCode='" + regionIsoCode + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", floor='" + floor + '\'' +
                ", section='" + section + '\'' +
                '}';
    }
}
