package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-30.
 */
public class VatResponse extends BaseAttributeResponse {
    private String vat;
    private String formatter;

    public VatResponse(final Long attributeValueId, final String vat, final String formatter) {
        this.id = attributeValueId;
        this.vat = vat;
        this.formatter = formatter;
    }

    public VatResponse() {
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
}
