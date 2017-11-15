package com.tingcore.cdc.charging.model;

import java.util.List;

public class ChargePoint {

    private Long id;
    private String assetName;
    private List<Connector> connectors;

    public ChargePoint() {}

    public ChargePoint(Long id, String assetName, List<Connector> connectors) {
        this.id = id;
        this.assetName = assetName;
        this.connectors = connectors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }
}
