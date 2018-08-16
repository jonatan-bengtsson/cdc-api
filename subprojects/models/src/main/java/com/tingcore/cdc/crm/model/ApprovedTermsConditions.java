package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedTermsConditions extends BaseAttributeModel {

    private final String url;
    private final String version;

    public ApprovedTermsConditions(final Long attributeValueId,
                                   final String url,
                                   final String version) {
        super(attributeValueId);
        this.url = url;
        this.version = version;
    }

    public ApprovedTermsConditions() {
        url = null;
        version = null;
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the stored approved terms and conditions", example = "http://chargedrive.com/terms-and-conditions")
    public String getUrl() {
        return url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved terms and conditions", example = "1.0")
    public String getVersion() {
        return version;
    }

    public ApprovedTermsConditions copyWithoutId() {
        return new ApprovedTermsConditions(null, url, version);
    }
}
