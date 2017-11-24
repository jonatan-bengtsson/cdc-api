package com.tingcore.cdc.crm.model;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class ApprovedMarketInfo extends BaseAttribute {
    private String marketInfoId;
    private String formatter;

    public ApprovedMarketInfo(Long valueId, String marketInfoId, String formatter) {
        this.id = valueId;
        this.marketInfoId = marketInfoId;
        this.formatter = formatter;
    }

    public ApprovedMarketInfo() {
    }

    public String getMarketInfoId() {
        return marketInfoId;
    }

    public void setMarketInfoId(String marketInfoId) {
        this.marketInfoId = marketInfoId;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
