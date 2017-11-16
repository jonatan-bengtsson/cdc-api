package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.Location;

import java.util.List;
import java.util.Optional;

public class ChargePointSite {

    public ChargePointSite() {}

    public ChargePointSite(Long id, String name, Location location, String description, ChargeSiteStatus status, List<ChargePoint> chargePoints, String imageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.status = status;
        this.chargePoints = chargePoints;
        this.imageUrl = imageUrl;
    }

    private Long id;
    private String name;
    private Location location;
    private String description;
    private ChargeSiteStatus status;
    private List<ChargePoint> chargePoints;
    private String imageUrl;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChargeSiteStatus getStatus() {
        return status;
    }

    public void setStatus(ChargeSiteStatus status) {
        this.status = status;
    }

    public List<ChargePoint> getChargePoints() {
        return chargePoints;
    }

    public void setChargePoints(List<ChargePoint> chargePoints) {
        this.chargePoints = chargePoints;
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }
}
