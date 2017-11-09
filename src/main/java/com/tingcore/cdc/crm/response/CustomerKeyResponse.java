package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@ApiModel
@JsonPropertyOrder({FieldConstant.ID, FieldConstant.KEY_NUMBER, FieldConstant.TYPE, FieldConstant.NAME, FieldConstant.ORGANIZATION_ID,
FieldConstant.ACTIVATED_FROM, FieldConstant.ACTIVATED_TO, FieldConstant.CREDITS, FieldConstant.DEFAULT_CURRENCY})
public class CustomerKeyResponse implements Serializable{

    private static final long serialVersionUID = -6763921790222839944L;

    private final Long id;
    private final String keyNumber;
    private final String type;
    private final String name;
    private final Long organizationId;
    private final Instant activatedFrom;
    private final Instant activatedTo;
    private final Integer credits;
    private final String defaultCurrency;

    public CustomerKeyResponse() {
        this.id = null;
        this.keyNumber = null;
        this.type = null;
        this.name = null;
        this.organizationId = null;
        this.activatedFrom = null;
        this.activatedTo = null;
        this.credits = null;
        this.defaultCurrency = null;
    }

    public CustomerKeyResponse(final Long id,
                               final String keyNumber,
                               final String type,
                               final String name,
                               final Long organizationId,
                               final Instant activatedFrom,
                               final Instant activatedTo,
                               final Integer credits,
                               final String defaultCurrency) {
        this.id = id;
        this.keyNumber = keyNumber;
        this.type = type;
        this.name = name;
        this.organizationId = organizationId;
        this.activatedFrom = activatedFrom;
        this.activatedTo = activatedTo;
        this.credits = credits;
        this.defaultCurrency = defaultCurrency;
    }

    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(position = 1, value = "The customer key id", example = "1", required = true)
    public Long getId() {
        return id;
    }

    @JsonProperty(FieldConstant.KEY_NUMBER)
    @ApiModelProperty(position = 2, value = "The customer key number", example = "60720400000936891", required = true)
    public String getKeyNumber() {
        return keyNumber;
    }

    @JsonProperty(FieldConstant.TYPE)
    @ApiModelProperty(position = 3, value = "The customer key type", example = "card", allowableValues = "card, tag, virtual", required = true)
    public String getType() {
        return type;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(position = 4, value = "The customer key name", example = "OrganizationPrefix1")
    public String getName() {
        return name;
    }

    @JsonProperty(FieldConstant.ORGANIZATION_ID)
    @ApiModelProperty(position = 5, value = "The providing organization id", example = "")
    public Long getOrganizationId() {
        return organizationId;
    }

    @JsonProperty(FieldConstant.ACTIVATED_FROM)
    @ApiModelProperty(position = 6, value = "Timestamp indicating the time of the activation", example = "1510230745348")
    public Instant getActivatedFrom() {
        return activatedFrom;
    }

    @JsonProperty(FieldConstant.ACTIVATED_TO)
    @ApiModelProperty(position = 7, value = "Timestamp indicating the time when the customer key should expire", example = "1510230745348")
    public Instant getActivatedTo() {
        return activatedTo;
    }

    @JsonProperty(FieldConstant.CREDITS)
    @ApiModelProperty(position = 8, value = /* todo credit ? */"TODO...", example = "100")
    public Integer getCredits() {
        return credits;
    }

    @JsonProperty(FieldConstant.DEFAULT_CURRENCY)
    @ApiModelProperty(position = 9, value = "The default currency", example = "100")
    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String keyNumber;
        private String type;
        private String name;
        private Long organizationId;
        private Instant activatedFrom;
        private Instant activatedTo;
        private Integer credits;

        private String defaultCurrency;

        private Builder() {
        }

        public Builder id(final Long id) {
            this.id = id;
            return this;
        }

        public Builder keyNumber(final String keyNumber) {
            this.keyNumber = keyNumber;
            return this;
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder organizationId(final Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder activatedFrom(final Instant activatedFrom) {
            this.activatedFrom = activatedFrom;
            return this;
        }

        public Builder activatedTo(final Instant activatedTo) {
            this.activatedTo = activatedTo;
            return this;
        }

        public Builder credits(final Integer credits) {
            this.credits = credits;
            return this;
        }

        public Builder defaultCurrency(final String defaultCurrency) {
            this.defaultCurrency = defaultCurrency;
            return this;
        }

        public CustomerKeyResponse build() {
            return new CustomerKeyResponse(id, keyNumber, type, name, organizationId, activatedFrom, activatedTo, credits, defaultCurrency);
        }
    }
}
