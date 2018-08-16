package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

import java.time.Instant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class InstantAttribute extends BaseAttributeModel {

    private final Instant value;

    public InstantAttribute(final Long valueId,
                            final Instant value) {
        super(valueId);
        this.value = value;
    }

    public InstantAttribute() {
        value = null;
    }

    @JsonProperty(FieldConstant.VALUE)
    public Instant getValue() {
        return value;
    }

    @Override
    public InstantAttribute copyWithoutId () {
        return new InstantAttribute(null, value);
    }
}
