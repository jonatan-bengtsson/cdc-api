package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-22.
 */
public class SocialSecurityNumber extends BaseAttributeModel {
    private String socialSecurityNumber;

    public SocialSecurityNumber() {
    }

    public SocialSecurityNumber(Long id, String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @JsonProperty(FieldConstant.SOCIAL_SECURITY_NUMBER)
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public SocialSecurityNumber copyWithoutId () {
        return new SocialSecurityNumber(null, socialSecurityNumber);
    }
}
