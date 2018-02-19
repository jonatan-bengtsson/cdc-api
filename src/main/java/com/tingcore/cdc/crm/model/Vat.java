package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

import javax.validation.constraints.NotNull;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class Vat extends BaseAttributeModel {

    private final String vat;
    private final String formatter;

    public Vat(final Long attributeValueId, final String vat, final String formatter) {
        super(attributeValueId);
        this.vat = vat;
        this.formatter = formatter;
    }

    public Vat() {
        this.vat = null;
        this.formatter = null;
    }

    @JsonProperty(FieldConstant.VAT)
    @NotNull
    public String getVat() {
        return vat;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    @Override
    public Vat copyWithoutId() {
        return new Vat(null, this.vat, this.formatter);
    }
}
