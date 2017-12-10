package com.tingcore.cdc.charging.model;

import com.tingcore.charging.assets.model.BasicConnector;

public class ChargePointTypeStatus {

    private BasicConnector.ConnectorTypeEnum type;
    private AggregatedChargePointTypeStatus aggregatedStatus;
    private int available = 0;
    private int occupied = 0;
    private int reserved = 0;
    private int outOfOrder = 0;
    private int availableQuickcharge = 0;
    private int occupiedQuickcharge = 0;
    private int reservedQuickcharge = 0;
    private int outOfOrderQuickcharge = 0;

    public ChargePointTypeStatus(AggregatedChargePointTypeStatus aggregatedStatus, BasicConnector.ConnectorTypeEnum type, int available, int occupied, int reserved, int outOfOrder, int availableQuickcharge, int occupiedQuickcharge, int reservedQuickcharge, int outOfOrderQuickcharge) {
        this.aggregatedStatus = aggregatedStatus;
        this.available = available;
        this.occupied = occupied;
        this.reserved = reserved;
        this.outOfOrder = outOfOrder;
        this.availableQuickcharge = availableQuickcharge;
        this.occupiedQuickcharge = occupiedQuickcharge;
        this.reservedQuickcharge = reservedQuickcharge;
        this.outOfOrderQuickcharge = outOfOrderQuickcharge;
        this.type = type;
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

    public BasicConnector.ConnectorTypeEnum getType() {
        return type;
    }

    public void setType(BasicConnector.ConnectorTypeEnum type) {
        this.type = type;
    }

    public int getAvailableQuickcharge() {
        return availableQuickcharge;
    }

    public void setAvailableQuickcharge(int availableQuickcharge) {
        this.availableQuickcharge = availableQuickcharge;
    }

    public int getOccupiedQuickcharge() {
        return occupiedQuickcharge;
    }

    public void setOccupiedQuickcharge(int occupiedQuickcharge) {
        this.occupiedQuickcharge = occupiedQuickcharge;
    }

    public int getReservedQuickcharge() {
        return reservedQuickcharge;
    }

    public void setReservedQuickcharge(int reservedQuickcharge) {
        this.reservedQuickcharge = reservedQuickcharge;
    }

    public int getOutOfOrderQuickcharge() {
        return outOfOrderQuickcharge;
    }

    public void setOutOfOrderQuickcharge(int outOfOrderQuickcharge) {
        this.outOfOrderQuickcharge = outOfOrderQuickcharge;
    }
}
