package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public abstract class BaseAttribute {
    Long id;

    public BaseAttribute() {
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
