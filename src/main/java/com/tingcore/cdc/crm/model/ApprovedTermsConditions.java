package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedTermsConditions extends BaseAttributeModel {
    private String url;
    private String version;

    public ApprovedTermsConditions(Long attributeValueId, String url, String version) {
        this.id = attributeValueId;
        this.url = url;
        this.version = version;
    }

    public ApprovedTermsConditions() {
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the approved terms and conditions.")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved terms and conditions.")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ApprovedTermsConditions copyWithoutId () {
        return new ApprovedTermsConditions(null, this.url, this.version);
    }
}
