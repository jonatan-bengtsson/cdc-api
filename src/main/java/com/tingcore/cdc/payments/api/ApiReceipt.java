package com.tingcore.cdc.payments.api;

import io.swagger.annotations.ApiModel;

@ApiModel
public class ApiReceipt {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
