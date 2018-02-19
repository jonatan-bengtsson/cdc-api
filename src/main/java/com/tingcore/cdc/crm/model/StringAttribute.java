package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class StringAttribute extends BaseAttributeModel {

    private final String value;

    public StringAttribute(final Long valueId, final String value) {
        super(valueId);
        this.value = value;
    }

    public StringAttribute() {
        this.value = null;
    }

    @JsonProperty(FieldConstant.VALUE)
    public String getValue() {
        return value;
    }

    public StringAttribute copyWithoutId() {
        return new StringAttribute(null, this.value);
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String value;

        private Builder() {
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public StringAttribute build() {
            return new StringAttribute(id, value);
        }
    }
}
