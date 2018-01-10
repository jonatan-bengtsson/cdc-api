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
    private final Long typeId;
    private final List<Long> userPaymentOptionIds;

    public CustomerKeyPostRequest(final String name,
                                  final String keyIdentifier,
                                  final Long typeId,
                                  final List<Long> userPaymentOptionIds) {
        this.name = name;
        this.keyIdentifier = keyIdentifier;
        this.typeId = typeId;
        this.userPaymentOptionIds = userPaymentOptionIds;
    }

    public CustomerKeyPostRequest() {
        this.name = null;
        this.keyIdentifier = null;
        this.typeId = null;
        this.userPaymentOptionIds = null;
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
    @Size(min = 1, max = 20) // The maximum size of a key identifier is 20 according to OCP
    @ApiModelProperty(position = 2, value = "The key identifier (often rfid) of the customer key", example = "0123456789", required = true)
    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    @JsonProperty(FieldConstant.TYPE_ID)
    @NotNull
    @ApiModelProperty(position = 3, value = "The customer key type id", example = "1", required = true)
    public Long getTypeId() {
        return typeId;
    }

    @JsonProperty(FieldConstant.USER_PAYMENT_OPTION_IDS)
    @ApiModelProperty(value = "A list of the user payment option IDs that can be used with the customer key")
    public List<Long> getUserPaymentOptionIds() {
        return userPaymentOptionIds;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String keyIdentifier;
        private Long typeId;
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

        public Builder typeId(final Long typeId) {
            this.typeId = typeId;
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
            return new CustomerKeyPostRequest(name, keyIdentifier, typeId, userPaymentOptions);
        }
    }
}
