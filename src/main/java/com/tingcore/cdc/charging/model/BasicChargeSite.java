package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.GeoCoordinate;

public class BasicChargeSite {
    private Long id;
    private String name;
    private GeoCoordinate coordinate;
    private ChargeSiteStatus quickStatus;
    private ChargeSiteStatus status;

    BasicChargeSite() {
    }

    public BasicChargeSite(Long id, String name, GeoCoordinate coordinate, ChargeSiteStatus chargeSiteStatus, ChargeSiteStatus quickChargeSiteStatus) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.status = chargeSiteStatus;
        this.quickStatus = quickChargeSiteStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public ChargeSiteStatus getQuickStatus() {
        return quickStatus;
    }

    public void setQuickStatus(ChargeSiteStatus quickStatus) {
        this.quickStatus = quickStatus;
    }

    public ChargeSiteStatus getStatus() {
        return status;
    }

    public void setStatus(ChargeSiteStatus status) {
        this.status = status;
    }
}
