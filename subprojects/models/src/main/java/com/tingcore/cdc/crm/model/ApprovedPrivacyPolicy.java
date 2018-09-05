package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-12-13.
 */
public class ApprovedPrivacyPolicy extends BaseAttributeModel {

    private final String url;
    private final String version;

    public ApprovedPrivacyPolicy(final Long attributeValueId,
                                 final String url,
                                 final String version) {
        super(attributeValueId);
        this.url = url;
        this.version = version;
    }

    public ApprovedPrivacyPolicy() {
        url = null;
        version = null;
    }

    @JsonProperty(FieldConstant.URL)
    @ApiModelProperty(value = "The url to the stored privacy policy", example = "http://chargedrive.com/privacy-policy")
    public String getUrl() {
        return url;
    }

    @JsonProperty(FieldConstant.VERSION)
    @ApiModelProperty(value = "The version of the approved privacy policy", example = "1.0")
    public String getVersion() {
        return version;
    }

    @Override
    public ApprovedPrivacyPolicy copyWithoutId() {
        return new ApprovedPrivacyPolicy(null, url, version);
    }
}
