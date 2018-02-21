package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class LicensePlate extends BaseAttributeModel {

    private final String licensePlate;
    private final String formatter;

    public LicensePlate(final Long id, final String licensePlate, final String formatter) {
        super(id);
        this.licensePlate = licensePlate;
        this.formatter = formatter;
    }

    public LicensePlate() {
        this.licensePlate = null;
        this.formatter = null;
    }

    @JsonProperty(FieldConstant.LICENSE_PLATE)
    public String getLicensePlate() {
        return licensePlate;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public LicensePlate copyWithoutId () {
        return new LicensePlate(null, this.licensePlate, this.formatter);
    }
}
