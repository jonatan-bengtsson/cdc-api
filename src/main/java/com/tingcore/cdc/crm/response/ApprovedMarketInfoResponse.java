package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class ApprovedMarketInfoResponse extends BaseAttributeResponse {
    private String marketInfoId;
    private String formatter;

    public ApprovedMarketInfoResponse(Long valueId, String marketInfoId, String formatter) {
        this.id = valueId;
        this.marketInfoId = marketInfoId;
        this.formatter = formatter;
    }

    public ApprovedMarketInfoResponse() {
    }

    @JsonProperty("marketInfoId")
    public String getMarketInfoId() {
        return marketInfoId;
    }

    public void setMarketInfoId(String marketInfoId) {
        this.marketInfoId = marketInfoId;
    }

    @JsonProperty(JsonPropertyConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
