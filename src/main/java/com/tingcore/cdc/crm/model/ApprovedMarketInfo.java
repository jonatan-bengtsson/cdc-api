package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.constant.JsonPropertyConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class ApprovedMarketInfo extends BaseAttributeModel {
    private String marketInfoId;
    private String formatter;

    public ApprovedMarketInfo(Long valueId, String marketInfoId, String formatter) {
        this.id = valueId;
        this.marketInfoId = marketInfoId;
        this.formatter = formatter;
    }

    public ApprovedMarketInfo() {
    }

    @JsonProperty(FieldConstant.MARKET_INFO_ID)
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

    @Override
    public ApprovedMarketInfo copyWithoutId () {
        return new ApprovedMarketInfo(null, this.marketInfoId,this.formatter);
    }
}
