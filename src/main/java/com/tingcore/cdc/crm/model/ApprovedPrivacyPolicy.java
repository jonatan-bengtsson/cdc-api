package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-12-13.
 */
public class ApprovedPrivacyPolicy extends BaseAttributeModel {
    private String url;
    private String version;

    public ApprovedPrivacyPolicy(Long attributeValueId, String url, String version) {
        this.id = attributeValueId;
        this.url = url;
        this.version = version;
    }

    public ApprovedPrivacyPolicy() {
    }

    @Override
    public ApprovedPrivacyPolicy copyWithoutId () {
        return new ApprovedPrivacyPolicy(null, this.url, this.version);
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the approved privacy policy.")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved privacy policy.")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
