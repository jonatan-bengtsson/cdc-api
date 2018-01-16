package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class LicensePlate extends BaseAttributeModel {
    private String licensePlate;
    private String formatter;

    public LicensePlate(Long id, String licensePlate, String formatter) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.formatter = formatter;
    }

    public LicensePlate() {
    }

    @JsonProperty(FieldConstant.LICENSE_PLATE)
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public LicensePlate copyWithoutId () {
        return new LicensePlate(null, this.licensePlate, this.formatter);
    }
}
