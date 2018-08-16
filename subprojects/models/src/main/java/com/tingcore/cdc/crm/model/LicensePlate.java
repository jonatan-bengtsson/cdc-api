package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class LicensePlate extends BaseAttributeModel {

    private final String licensePlate;

    public LicensePlate(final Long id, final String licensePlate) {
        super(id);
        this.licensePlate = licensePlate;
    }

    public LicensePlate() {
        licensePlate = null;
    }

    @JsonProperty(FieldConstant.LICENSE_PLATE)
    public String getLicensePlate() {
        return licensePlate;
    }

    public LicensePlate copyWithoutId () {
        return new LicensePlate(null, licensePlate);
    }
}
