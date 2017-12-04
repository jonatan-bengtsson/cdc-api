package com.tingcore.cdc.crm.response;

import java.time.Instant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class InstantAttributeResponse extends BaseAttributeResponse {
    private Instant value;

    public InstantAttributeResponse(final Long valueId, final Instant value) {
        this.id = valueId;
        this.value = value;
    }

    public InstantAttributeResponse() {
    }

    public Instant getValue() {
        return value;
    }

    public void setValue(final Instant value) {
        this.value = value;
    }
}
