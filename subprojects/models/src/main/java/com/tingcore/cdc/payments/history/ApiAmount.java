package com.tingcore.cdc.payments.history;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ApiAmount {

    @JsonProperty("inclVat")
    public String getInclVat();
    @JsonProperty("exclVat")
    public String getExclVat();
    @JsonProperty("currency")
    public String getCurrency();
    @JsonProperty("vat")
    public String getVat();
}
