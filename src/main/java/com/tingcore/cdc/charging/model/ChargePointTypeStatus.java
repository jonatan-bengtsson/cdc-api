package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.Connector;

public class ChargePointTypeStatus {

    private Connector.ConnectorTypeEnum type;
    private AggregatedChargePointTypeStatus aggregatedStatus;
    private boolean isQuickCharge;
    private int available = 0;
    private int occupied = 0;
    private int reserved = 0;
    private int outOfOrder = 0;

    public ChargePointTypeStatus() {}

    public ChargePointTypeStatus(AggregatedChargePointTypeStatus aggregatedStatus, int available, int occupied, int reserved, int outOfOrder, boolean isQuickCharge) {
        this.isQuickCharge = isQuickCharge;
        this.aggregatedStatus = aggregatedStatus;
        this.available = available;
        this.occupied = occupied;
        this.reserved = reserved;
        this.outOfOrder = outOfOrder;
    }

    public AggregatedChargePointTypeStatus getAggregatedStatus() {
        return aggregatedStatus;
    }

    public void setAggregatedStatus(AggregatedChargePointTypeStatus aggregatedStatus) {
        this.aggregatedStatus = aggregatedStatus;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }


    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }


    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }


    public int getOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(int outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public Connector.ConnectorTypeEnum getType() {
        return type;
    }

    public void setType(Connector.ConnectorTypeEnum type) {
        this.type = type;
    }

    public boolean isQuickCharge() {
        return isQuickCharge;
    }

    public void setQuickCharge(boolean quickCharge) {
        isQuickCharge = quickCharge;
    }
}
