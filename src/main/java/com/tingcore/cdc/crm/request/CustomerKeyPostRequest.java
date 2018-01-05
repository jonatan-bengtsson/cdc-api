package com.tingcore.cdc.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author palmithor
 * @since 2017-12-12
 */
public class CustomerKeyPostRequest {

    private final String name;
    private final String keyIdentifier; // TODO we might have to add more detailed validation constraints for this field
    private final List<Long> userPaymentOptions;

    public CustomerKeyPostRequest(final String name,
                                  final String keyIdentifier,
                                  final List<Long> userPaymentOptions) {
        this.name = name;
        this.keyIdentifier = keyIdentifier;
        this.userPaymentOptions = userPaymentOptions;
    }

    public CustomerKeyPostRequest() {
        this.name = null;
        this.keyIdentifier = null;
        this.userPaymentOptions = null;
    }

    @JsonProperty(FieldConstant.NAME)
    @NotNull
    @Size(min = 1, max = 40)
    @ApiModelProperty(value = "The customer key given name", example = "My blue tag", required = true)
    public String getName() {
        return name;
    }

    @JsonProperty(FieldConstant.KEY_IDENTIFIER)
    @NotNull
    @Size(min = 1, max = 255)
    @ApiModelProperty(position = 2, value = "The key identifier (often rfid) of the customer key", example = "0123456789", required = true)
    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    @JsonProperty(FieldConstant.USER_PAYMENT_OPTIONS)
    @ApiModelProperty(value = "A list of the user payment option IDs that can be used with the customer key")
    public List<Long> getUserPaymentOptions() {
        return userPaymentOptions;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String keyIdentifier;
        private List<Long> userPaymentOptions;

        private Builder() {
            this.userPaymentOptions = new ArrayList<>();
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder keyIdentifier(final String keyIdentifier) {
            this.keyIdentifier = keyIdentifier;
            return this;
        }

        public Builder addUserPaymentOption(final Long userPaymentOptionId) {
            this.userPaymentOptions.add(userPaymentOptionId);
            return this;
        }

        public Builder userPaymentOptions(final List<Long> userPaymentOptions) {
            this.userPaymentOptions = userPaymentOptions;
            return this;
        }

        public CustomerKeyPostRequest build() {
            return new CustomerKeyPostRequest(name, keyIdentifier, userPaymentOptions);
        }
    }
}
