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
        this.id = valueId;
        this.url = url;
        this.version = version;
    }

    public ApprovedMarketInfo() {
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the approved market info document.")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved market info document.")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public ApprovedMarketInfo copyWithoutId () {
        return new ApprovedMarketInfo(null, this.url, this.version);
    }
}
