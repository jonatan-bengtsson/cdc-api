package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-13.
 */
public class PhoneNumber extends BaseAttributeModel {

    private final String name;
    private final String phoneNumber;

    public PhoneNumber(final Long valueId,
                       final String phoneNumber,
                       final String name) {
        super(valueId);
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public PhoneNumber() {
        phoneNumber = null;
        name = null;
    }

    @JsonProperty(FieldConstant.PHONE_NUMBER)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty(FieldConstant.NAME)
    public String getName() {
        return name;
    }

    public PhoneNumber copyWithoutId() {
        return new PhoneNumber(null, phoneNumber, name);
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String phoneNumber;
        private Long id;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public PhoneNumber build() {
            return new PhoneNumber(id, phoneNumber, name);
        }
    }
}
