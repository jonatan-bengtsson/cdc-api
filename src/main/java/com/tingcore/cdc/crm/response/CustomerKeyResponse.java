package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@ApiModel
@JsonPropertyOrder({FieldConstant.ID, FieldConstant.KEY_NUMBER, FieldConstant.TYPE, FieldConstant.NAME, FieldConstant.ORGANIZATION_ID,
        FieldConstant.ACTIVE_FROM, FieldConstant.ACTIVE_TO, FieldConstant.IS_SERVICE_KEY, FieldConstant.CHARGE_GROUP_IDS,
        FieldConstant.PAYMENT_INFORMATION, FieldConstant.PAYMENT_INFORMATION, FieldConstant.DEFAULT_CURRENCY})
public class CustomerKeyResponse implements Serializable {

    private static final long serialVersionUID = -6763921790222839944L;

    private final Long id;
    private final String keyNumber;
    private final String type;
    private final String name;
    private final Long organizationId;
    private final Instant activeFrom;
    private final Instant activeTo;
    private final Boolean serviceKey;
    private final List<Long> chargeGroupIds;
    private final PaymentInformationResponse paymentInformationResponse;
    private final String defaultCurrency;

    public CustomerKeyResponse() {
        this.id = null;
        this.keyNumber = null;
        this.type = null;
        this.name = null;
        this.organizationId = null;
        this.activeFrom = null;
        this.activeTo = null;
        this.serviceKey = null;
        this.chargeGroupIds = null;
        this.paymentInformationResponse = null;
        this.defaultCurrency = null;
    }

    public CustomerKeyResponse(final Long id,
                               final String keyNumber,
                               final String type,
                               final String name,
                               final Long organizationId,
                               final Instant activeFrom,
                               final Instant activeTo,
                               final Boolean serviceKey,
                               final List<Long> chargeGroupIds,
                               final PaymentInformationResponse paymentInformationResponse,
                               final String defaultCurrency) {
        this.id = id;
        this.keyNumber = keyNumber;
        this.type = type;
        this.name = name;
        this.organizationId = organizationId;
        this.activeFrom = activeFrom;
        this.activeTo = activeTo;
        this.serviceKey = serviceKey;
        this.chargeGroupIds = chargeGroupIds;
        this.paymentInformationResponse = paymentInformationResponse;
        this.defaultCurrency = defaultCurrency;
    }

    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(position = 1, value = "The customer key id", example = "pNK7rWbjGP", required = true)
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
    @ApiModelProperty(position = 5, value = "The providing organization id", example = "pNK7rWbjGP")
    public Long getOrganizationId() {
        return organizationId;
    }

    @JsonProperty(FieldConstant.ACTIVE_FROM)
    @ApiModelProperty(position = 6, value = "Timestamp indicating the time of the activation", example = "1510230745348")
    public Instant getActiveFrom() {
        return activeFrom;
    }

    @JsonProperty(FieldConstant.ACTIVE_TO)
    @ApiModelProperty(position = 7, value = "Timestamp indicating the time when the customer key should expire", example = "1510230745348")
    public Instant getActiveTo() {
        return activeTo;
    }

    @JsonProperty(FieldConstant.IS_SERVICE_KEY)
    @ApiModelProperty(position = 8, value = "Boolean specifying if this customer key is a service key", example = "false", required = true)
    public Boolean getServiceKey() {
        return serviceKey;
    }

    @JsonProperty(FieldConstant.CHARGE_GROUP_IDS)
    @ApiModelProperty(position = 9, value = "The charging groups this customer key has access to", example = "[\"pNK7rWbjGP\"]")
    public List<Long> getChargeGroupIds() {
        return chargeGroupIds;
    }

    @JsonProperty(FieldConstant.PAYMENT_INFORMATION)
    @ApiModelProperty(position = 10, value = "The payment profile associated with this user key")
    public PaymentInformationResponse getPaymentInformationResponse() {
        return paymentInformationResponse;
    }

    @JsonProperty(FieldConstant.DEFAULT_CURRENCY)
    @ApiModelProperty(position = 11, value = "The default currency", example = "100")
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
        private Instant activeFrom;
        private Instant activeTo;
        private Boolean serviceKey;
        private List<Long> chargeGroupIds;
        private PaymentInformationResponse paymentInformationResponse;
        private String defaultCurrency;

        private Builder() {
        }

        public static Builder aCustomerKeyResponse() {
            return new Builder();
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

        public Builder activeFrom(final Instant activeFrom) {
            this.activeFrom = activeFrom;
            return this;
        }

        public Builder activeTo(final Instant activeTo) {
            this.activeTo = activeTo;
            return this;
        }

        public Builder serviceKey(final Boolean serviceKey) {
            this.serviceKey = serviceKey;
            return this;
        }

        public Builder chargeGroupIds(final List<Long> chargeGroupIds) {
            this.chargeGroupIds = chargeGroupIds;
            return this;
        }

        public Builder paymentInformation(final PaymentInformationResponse paymentInformationResponse) {
            this.paymentInformationResponse = paymentInformationResponse;
            return this;
        }

        public Builder defaultCurrency(final String defaultCurrency) {
            this.defaultCurrency = defaultCurrency;
            return this;
        }

        public CustomerKeyResponse build() {
            return new CustomerKeyResponse(id, keyNumber, type, name, organizationId, activeFrom, activeTo, serviceKey, chargeGroupIds, paymentInformationResponse, defaultCurrency);
        }
    }
}
