package com.tingcore.cdc.charging.mapper;

public class StatusAccumulator {
    private int available = 0;
    private int occupied = 0;
    private int reserved = 0;
    private int outOfOrder = 0;
    private int availableQuickcharge = 0;
    private int occupiedQuickcharge = 0;
    private int reservedQuickcharge = 0;
    private int outOfOrderQuickcharge = 0;


    public void incAvailable() {
        available++;
    }
    public void incOccupied() {
        occupied++;
    }
    public void incReserved() {
        reserved++;
    }
    public void incOutOfOrder() {
        outOfOrder++;
    }


    public void incAvailableQuickcharge() {
        availableQuickcharge++;
    }
    public void incOccupiedQuickcharge() {
        occupiedQuickcharge++;
    }
    public void incReservedQuickcharge() {
        reservedQuickcharge++;
    }
    public void incOutOfOrderQuickcharge() {
        outOfOrderQuickcharge++;
    }

    public int getAvailable() {
        return available;
    }
    public int getOccupied() {
        return occupied;
    }
    public int getReserved() {
        return reserved;
    }
    public int getOutOfOrder() {
        return outOfOrder;
    }

    public int getAvailableQuickcharge() {
        return availableQuickcharge;
    }

    public int getOccupiedQuickcharge() {
        return occupiedQuickcharge;
    }

    public int getReservedQuickcharge() {
        return reservedQuickcharge;
    }

    public int getOutOfOrderQuickcharge() {
        return outOfOrderQuickcharge;
    }
}
