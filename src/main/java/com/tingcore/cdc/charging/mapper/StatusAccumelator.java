package com.tingcore.cdc.charging.mapper;

public class StatusAccumelator {
    private int available = 0;
    private int occupied = 0;
    private int reserved = 0;
    private int outOfOrder = 0;


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

}
