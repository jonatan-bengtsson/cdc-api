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

    public Vat(final Long attributeValueId, final String vat) {
        super(attributeValueId);
        this.vat = vat;
    }

    public Vat() {
        vat = null;
    }

    @JsonProperty(FieldConstant.VAT) public @NotNull String getVat() {
        return vat;
    }

    @Override
    public Vat copyWithoutId() {
        return new Vat(null, vat);
    }
}
