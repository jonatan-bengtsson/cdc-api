package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.commons.address.GenericAddress;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

public class CustomerKeyOrderRequest {

    private final GenericAddress address;
    private final Integer quantity;

    public CustomerKeyOrderRequest(GenericAddress address, Integer quantity) {
        this.address = address;
        this.quantity = quantity;
    }

    public CustomerKeyOrderRequest() {
        address = null;
        quantity = null;
    }

    @JsonProperty("address")
    @NotNull
    @ApiModelProperty(value = "The address the order should be shipped to", example = "Vasagatan2, Stockholm 18123, Sweden", required = true)
    public GenericAddress getAddress() {
        return address;
    }

    @JsonProperty("quantity")
    @Range(min = 1, max = 10)
    @ApiModelProperty(value = "How many customer keys the order should contain", example = "1", required = true)
    public Integer getQuantity() {
        return quantity;
    }
}
