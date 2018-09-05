package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class ApprovedMarketInfo extends BaseAttributeModel {
    private String url;
    private String version;

    public ApprovedMarketInfo(Long valueId, String url, String version) {
        super(valueId);
        this.url = url;
        this.version = version;
    }

    public ApprovedMarketInfo() {
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the stored market info", example = "http://chargedrive.com/market-info")
    public String getUrl() {
        return url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved market info", example = "1.0")
    public String getVersion() {
        return version;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public ApprovedMarketInfo copyWithoutId () {
        return new ApprovedMarketInfo(null, url, version);
    }
}
