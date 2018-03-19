package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerKeyOrderRequest {

    private final String address;
    private final Integer quantity;

    public CustomerKeyOrderRequest(String address, Integer quantity) {
        this.address = address;
        this.quantity = quantity;
    }

    public CustomerKeyOrderRequest() {
        address = null;
        quantity = null;
    }

    @JsonProperty("address")
    @NotNull
    @Size(min = 5, max = 1000)
    @ApiModelProperty(value = "The address the order should be shipped to", example = "Vasagatan2, Stockholm 18123, Sweden", required = true)
    public String getAddress() {
        return address;
    }

    @JsonProperty("quantity")
    @Range(min = 1, max = 10)
    @ApiModelProperty(value = "How many customer keys the order should contain", example = "1", required = true)
    public Integer getQuantity() {
        return quantity;
    }
}
