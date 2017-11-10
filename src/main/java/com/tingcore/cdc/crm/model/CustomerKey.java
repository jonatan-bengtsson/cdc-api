package com.tingcore.cdc.crm.model;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2017-11-09
 */
public class CustomerKey {

    private final String keyNumber;
    private final String type;
    private final String name;
    private final Long organizationId;
    private final Instant activatedFrom;
    private final Instant activatedTo;
    private final String defaultCurrency;
    private final Boolean isServiceKey;

    public CustomerKey() {
        this.keyNumber = null;
        this.type = null;
        this.name = null;
        this.organizationId = null;
        this.activatedFrom = null;
        this.activatedTo = null;
        this.defaultCurrency = null;
        this.isServiceKey = null;
    }

    public CustomerKey(final String keyNumber,
                       final String type,
                       final String name,
                       final Long organizationId,
                       final Instant activatedFrom,
                       final Instant activatedTo,
                       final String defaultCurrency,
                       final Boolean isServiceKey) {
        this.keyNumber = keyNumber;
        this.type = type;
        this.name = name;
        this.organizationId = organizationId;
        this.activatedFrom = activatedFrom;
        this.activatedTo = activatedTo;
        this.defaultCurrency = defaultCurrency;
        this.isServiceKey = isServiceKey;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public Instant getActivatedFrom() {
        return activatedFrom;
    }

    public Instant getActivatedTo() {
        return activatedTo;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public Boolean getServiceKey() {
        return isServiceKey;
    }
}
