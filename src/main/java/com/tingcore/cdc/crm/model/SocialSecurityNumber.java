package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-22.
 */
public class SocialSecurityNumber extends BaseAttributeModel {
    private String socialSecurityNumber;
    private String formatter;

    public SocialSecurityNumber() {
    }

    public SocialSecurityNumber(Long id, String socialSecurityNumber, String formatter) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.formatter = formatter;
    }

    @JsonProperty(FieldConstant.SOCIAL_SECURITY_NUMBER)
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @JsonProperty(FieldConstant.FORMATTER)
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }
}
