package com.tingcore.cdc.crm.response;

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
