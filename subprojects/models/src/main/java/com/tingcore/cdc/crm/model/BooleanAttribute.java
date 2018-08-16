package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class BooleanAttribute extends BaseAttributeModel {

    private final Boolean value;

    public BooleanAttribute(final Long valueId,
                            final Boolean value) {
        super(valueId);
        this.value = value;
    }

    public BooleanAttribute() {
        value = null;
    }

    @JsonProperty(FieldConstant.VALUE)
    public Boolean getValue() {
        return value;
    }

    @Override
    public BooleanAttribute copyWithoutId () {
        return new BooleanAttribute(null, value);
    }

    public static Builder createBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private Boolean value;
        private Long id;

        private Builder() {
        }

        public Builder value(Boolean value) {
            this.value = value;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public BooleanAttribute build() {
            return new BooleanAttribute(id, value);
        }
    }
}
