package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.List;

/**
 * @author palmithor
 * @since 2017-12-19
 */
public class CustomerKey extends BaseModel {

    private final String name;
    private final String keyIdentifier;
    private final Long bookkeepingAccountId;
    private final Boolean enabled;
    private final Instant validFrom;
    private final Instant validTo;
    private final List<UserPaymentOption> userPaymentOptions;

    public CustomerKey(final Long id,
                       final Instant created,
                       final Instant updated,
                       final String name,
                       final String keyIdentifier,
                       final Long bookkeepingAccountId,
                       final Boolean enabled,
                       final Instant validFrom,
                       final Instant validTo,
                       final List<UserPaymentOption> userPaymentOptions) {
        super(id, created, updated);
        this.name = name;
        this.keyIdentifier = keyIdentifier;
        this.bookkeepingAccountId = bookkeepingAccountId;
        this.enabled = enabled;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.userPaymentOptions = userPaymentOptions;
    }


    public CustomerKey() {
        this.name = null;
        this.keyIdentifier = null;
        this.bookkeepingAccountId = null;
        this.enabled = null;
        this.validFrom = null;
        this.validTo = null;
        this.userPaymentOptions = null;
    }

    @JsonProperty(FieldConstant.NAME)
    @ApiModelProperty(value = "The name of the customer key given by its creator")
    public String getName() {
        return name;
    }

    @JsonProperty(FieldConstant.KEY_IDENTIFIER)
    @ApiModelProperty(value = "The key identifier of the customer key. RFID for example.")
    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    @JsonProperty(FieldConstant.BOOKKEEPING_ACCOUNT_ID)
    @ApiModelProperty(value = "The bookkeeping account id.")
    public Long getBookkeepingAccountId() {
        return bookkeepingAccountId;
    }

    @JsonProperty(FieldConstant.IS_ENABLED)
    @ApiModelProperty(value = "A property which indicates if the customer key is enabled or not")
    public Boolean getEnabled() {
        return enabled;
    }

    @JsonProperty(FieldConstant.VALID_FROM)
    @ApiModelProperty(value = "From when the customer key is valid")
    public Instant getValidFrom() {
        return validFrom;
    }

    @JsonProperty(FieldConstant.VALID_TO)
    @ApiModelProperty(value = "To when the customer key is valdi")
    public Instant getValidTo() {
        return validTo;
    }

    @JsonProperty(FieldConstant.USER_PAYMENT_OPTIONS)
    @ApiModelProperty(value = "A list of the affiliated user payment options")
    public List<UserPaymentOption> getUserPaymentOptions() {
        return userPaymentOptions;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Instant created;
        private Instant updated;
        private String name;
        private String keyIdentifier;
        private Long bookkeepingAccountId;
        private Boolean isEnabled;
        private Instant validFrom;
        private Instant validTo;

        private List<UserPaymentOption> userPaymentOptions;

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

        public Builder updated(final Instant updated) {
            this.updated = updated;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder keyIdentifier(final String keyIdentifier) {
            this.keyIdentifier = keyIdentifier;
            return this;
        }

        public Builder bookkeepingAccountId(final Long bookkeepingAccountId) {
            this.bookkeepingAccountId = bookkeepingAccountId;
            return this;
        }

        public Builder isEnabled(final Boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public Builder validFrom(final Instant validFrom) {
            this.validFrom = validFrom;
            return this;
        }

        public Builder validTo(final Instant validTo) {
            this.validTo = validTo;
            return this;
        }

        public Builder userPaymentOptions(final List<UserPaymentOption> userPaymentOptions) {
            this.userPaymentOptions = userPaymentOptions;
            return this;
        }

        public CustomerKey build() {
            return new CustomerKey(id, created, updated, name, keyIdentifier, bookkeepingAccountId, isEnabled, validFrom, validTo, userPaymentOptions);
        }
    }
}
