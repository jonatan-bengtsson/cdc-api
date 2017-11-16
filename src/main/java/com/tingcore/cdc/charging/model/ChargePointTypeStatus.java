package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.Connector;

public class ChargePointTypeStatus {

    private Connector.ConnectorTypeEnum type;
    private AggregatedChargePointTypeStatus aggregatedStatus;
    private int available = 0;
    private int availableQuickcharge = 0;
    private int occupied = 0;
    private int occupiedQuickcharge = 0;
    private int reserved = 0;
    private int reservedQuickcharge = 0;
    private int outOfOrder = 0;
    private int outOfOrderQuickcharge = 0;

    public ChargePointTypeStatus() {}

    public ChargePointTypeStatus(AggregatedChargePointTypeStatus aggregatedStatus, int available, int availableQuickcharge, int occupied, int occupiedQuickcharge, int reserved, int reservedQuickcharge, int outOfOrder, int outOfOrderQuickcharge) {
        this.aggregatedStatus = aggregatedStatus;
        this.available = available;
        this.availableQuickcharge = availableQuickcharge;
        this.occupied = occupied;
        this.occupiedQuickcharge = occupiedQuickcharge;
        this.reserved = reserved;
        this.reservedQuickcharge = reservedQuickcharge;
        this.outOfOrder = outOfOrder;
        this.outOfOrderQuickcharge = outOfOrderQuickcharge;
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

    public int getAvailableQuickcharge() {
        return availableQuickcharge;
    }

    public void setAvailableQuickcharge(int availableQuickcharge) {
        this.availableQuickcharge = availableQuickcharge;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getOccupiedQuickcharge() {
        return occupiedQuickcharge;
    }

    public void setOccupiedQuickcharge(int occupiedQuickcharge) {
        this.occupiedQuickcharge = occupiedQuickcharge;
    }

    public int getReserved() {
        return reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getReservedQuickcharge() {
        return reservedQuickcharge;
    }

    public void setReservedQuickcharge(int reservedQuickcharge) {
        this.reservedQuickcharge = reservedQuickcharge;
    }

    public int getOutOfOrder() {
        return outOfOrder;
    }

    public void setOutOfOrder(int outOfOrder) {
        this.outOfOrder = outOfOrder;
    }

    public int getOutOfOrderQuickcharge() {
        return outOfOrderQuickcharge;
    }

    public void setOutOfOrderQuickcharge(int outOfOrderQuickcharge) {
        this.outOfOrderQuickcharge = outOfOrderQuickcharge;
    }
}
