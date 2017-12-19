package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class LicensePlateResponse extends BaseAttributeResponse {
    private String licensePlate;
    private String formatter;

    public LicensePlateResponse(String licenseplate, String formatter) {
        this.licensePlate = licenseplate;
        this.formatter = formatter;
    }

    public LicensePlateResponse() {
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
}
