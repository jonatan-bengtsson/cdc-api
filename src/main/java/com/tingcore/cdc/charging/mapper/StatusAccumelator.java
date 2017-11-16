package com.tingcore.cdc.charging.mapper;

public class StatusAccumelator {
    private int available = 0;
    private int availableQuickcharge = 0;
    private int occupied = 0;
    private int occupiedQuickcharge = 0;
    private int reserved = 0;
    private int reservedQuickcharge = 0;
    private int outOfOrder = 0;
    private int outOfOrderQuickcharge = 0;


    public void incAvailable() {
        available++;
    }

    public void incAvailableQuickcharge() {
        availableQuickcharge++;
    }

    public void incOccupied() {
        occupied++;
    }

    public void incOccupiedQuickcharge() {
        occupiedQuickcharge++;
    }

    public void incReserved() {
        reserved++;
    }

    public void incReservedQuickcharge() {
        reservedQuickcharge++;
    }

    public void incOutOfOrder() {
        outOfOrder++;
    }

    public void incOutOfOrderQuickcharge() {
        outOfOrderQuickcharge++;
    }

    public int getAvailable() {
        return available;
    }

    public int getAvailableQuickcharge() {
        return availableQuickcharge;
    }

    public int getOccupied() {
        return occupied;
    }

    public int getOccupiedQuickcharge() {
        return occupiedQuickcharge;
    }

    public int getReserved() {
        return reserved;
    }

    public int getReservedQuickcharge() {
        return reservedQuickcharge;
    }

    public int getOutOfOrder() {
        return outOfOrder;
    }

    public int getOutOfOrderQuickcharge() {
        return outOfOrderQuickcharge;
    }
}
