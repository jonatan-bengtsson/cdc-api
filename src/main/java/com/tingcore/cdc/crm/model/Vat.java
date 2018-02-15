package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

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

    @JsonProperty(FieldConstant.VAT)
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    @Override
    public Vat copyWithoutId () {
        return new Vat(null, this.vat, this.formatter);
    }
}
