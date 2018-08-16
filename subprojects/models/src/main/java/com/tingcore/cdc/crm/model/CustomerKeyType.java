package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2018-01-09
 */
public class CustomerKeyType extends BaseModel {

    private final String name;
    private final String description;

    public CustomerKeyType(final Long id, final Instant created, final Instant updated, final String name, final String description) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
    }

    public CustomerKeyType() {
        this.name = null;
        this.description = null;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(position = 1, value = "The customer key type name (or title)", example = "tag", required = true)
    public String getName() {
        return name;
    }

    @JsonProperty(FieldConstant.DESCRIPTION)
    @ApiModelProperty(position = 2, value = "A description of the customer key type", example = "A RFID tag", required = true)
    public String getDescription() {
        return description;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Instant created;
        private String name;
        private Instant updated;
        private String description;

        private Builder() {
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder created(final Instant created) {
            this.created = created;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder updated(final Instant updated) {
            this.updated = updated;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public CustomerKeyType build() {
            return new CustomerKeyType(id, created, updated, name, description);
        }
    }
}
