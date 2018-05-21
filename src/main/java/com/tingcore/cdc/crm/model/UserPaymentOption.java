package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.users.model.v1.response.PaymentOptionResponse;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

/**
 * @author palmithor
 * @since 2017-12-19
 */
public class UserPaymentOption extends BaseModel {

    private final String name;
    private final String description;
    private final PaymentOptionResponse paymentOption;
    private final String paymentOptionReference;

    public UserPaymentOption(final Long id, final Instant created, final Instant updated, final String name, final String description, final PaymentOptionResponse paymentOption, final String paymentOptionReference) {
        super(id, created, updated);
        this.name = name;
        this.description = description;
        this.paymentOption = paymentOption;
        this.paymentOptionReference = paymentOptionReference;
    }

    public UserPaymentOption() {
        this.name = null;
        this.description = null;
        this.paymentOption = null;
        this.paymentOptionReference = null;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(value = "The name of the payment option given by its creator")
    public String getName() {
        return name;
    }

    @JsonProperty(FieldConstant.DESCRIPTION)
    @ApiModelProperty(value = "The name of the payment option given by its creator")
    public String getDescription() {
        return description;
    }

    @JsonProperty(FieldConstant.PAYMENT_OPTION)
    @ApiModelProperty(value = "The payment option type")
    public PaymentOptionResponse getPaymentOption() {
        return paymentOption;
    }

    @JsonProperty(FieldConstant.PAYMENT_OPTION_REFERENCE)
    @ApiModelProperty(value = "The payment option reference to the payment method")
    public String getPaymentOptionReference() {
        return paymentOptionReference;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Instant created;
        private String description;
        private Instant updated;
        private PaymentOptionResponse paymentOption;
        private String paymentOptionReference;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder updated(Instant updated) {
            this.updated = updated;
            return this;
        }

        public Builder paymentOption(PaymentOptionResponse paymentOption) {
            this.paymentOption = paymentOption;
            return this;
        }

        public Builder paymentOptionReference(String paymentOptionReference) {
            this.paymentOptionReference = paymentOptionReference;
            return this;
        }

        public UserPaymentOption build() {
            return new UserPaymentOption(id, created, updated, name, description, paymentOption, paymentOptionReference);
        }
    }
}
