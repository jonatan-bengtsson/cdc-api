package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class Vat extends BaseAttributeModel {
    private String vat;
    private String formatter;

    public Vat(final Long attributeValueId, final String vat, final String formatter) {
        this.id = attributeValueId;
        this.vat = vat;
        this.formatter = formatter;
    }

    public Vat() {
    }

    @JsonProperty("VAT")
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @JsonProperty("formatter")
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
