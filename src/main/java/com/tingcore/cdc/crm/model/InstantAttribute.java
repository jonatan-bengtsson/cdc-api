package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

import java.time.Instant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class InstantAttribute extends BaseAttributeModel {
    private Instant value;

    public InstantAttribute(final Long valueId, final Instant value) {
        this.id = valueId;
        this.value = value;
    }

    public InstantAttribute() {
    }

    @JsonProperty(JsonPropertyConstant.VALUE)
    public Instant getValue() {
        return value;
    }

    public void setValue(final Instant value) {
        this.value = value;
    }
}
