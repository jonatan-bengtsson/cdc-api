package com.tingcore.cdc.crm.request;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class CustomerKeyOrderRequest {

    @NotNull
    private final String address;
    @Range(min = 1, max = 10)
    private final Integer quantity;

    public CustomerKeyOrderRequest(String address, Integer quantity) {
        this.address = address;
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
