package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.Location;

public class MapPreviewChargeSite {
    private long id;
    private String name;
    private Location location;
    private ChargeSiteStatus chargeSiteStatus;

    MapPreviewChargeSite() {
    }

    public MapPreviewChargeSite(long id, String name, Location location, ChargeSiteStatus chargeSiteStatus) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.chargeSiteStatus = chargeSiteStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ChargeSiteStatus getChargeSiteStatus() {
        return chargeSiteStatus;
    }

    public void setChargeSiteStatus(ChargeSiteStatus chargeSiteStatus) {
        this.chargeSiteStatus = chargeSiteStatus;
    }
}
