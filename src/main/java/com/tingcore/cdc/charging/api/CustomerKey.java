package com.tingcore.cdc.charging.api;

import io.swagger.annotations.ApiModel;

import static org.apache.commons.lang3.Validate.notBlank;

@ApiModel
public class CustomerKey {
    public final String id;
    public final String name;

    public CustomerKey(final String id,
                       final String name) {
        this.id = notBlank(id);
        this.name = notBlank(name);
    }
}
