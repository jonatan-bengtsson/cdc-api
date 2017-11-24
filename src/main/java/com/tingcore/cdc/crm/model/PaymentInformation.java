package com.tingcore.cdc.crm.model;

import java.time.Instant;

/**
 * TODO this might completely change
 *
 * @author palmithor
 * @since 2017-11-10
 */
public class PaymentInformation {

    private final Boolean prePaid;
    private final Integer prePaidAmount;
    private final Integer balance;
    private final Instant prepaidExpiry;
    private final PaymentMethod paymentMethodResponse;

    public PaymentInformation() {
        this.prePaid = null;
        this.prePaidAmount = null;
        this.balance = null;
        this.prepaidExpiry = null;
        this.paymentMethodResponse = null;
    }

    public PaymentInformation(final Boolean prePaid,
                              final Integer prePaidAmount,
                              final Integer balance,
                              final Instant prepaidExpiry,
                              final PaymentMethod paymentMethodResponse) {
        this.prePaid = prePaid;
        this.prePaidAmount = prePaidAmount;
        this.balance = balance;
        this.prepaidExpiry = prepaidExpiry;
        this.paymentMethodResponse = paymentMethodResponse;
    }

    public Boolean getPrePaid() {
        return prePaid;
    }

    public Integer getPrePaidAmount() {
        return prePaidAmount;
    }

    public Integer getBalance() {
        return balance;
    }

    public Instant getPrepaidExpiry() {
        return prepaidExpiry;
    }

    public PaymentMethod getPaymentMethodResponse() {
        return paymentMethodResponse;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean prePaid;
        private Integer prePaidAmount;
        private Integer balance;
        private Instant prepaidExpiry;

        private PaymentMethod paymentMethodResponse;

        private Builder() {
        }

        public Builder prePaid(Boolean prePaid) {
            this.prePaid = prePaid;
            return this;
        }

        public Builder prePaidAmount(Integer prePaidAmount) {
            this.prePaidAmount = prePaidAmount;
            return this;
        }

        public Builder balance(Integer balance) {
            this.balance = balance;
            return this;
        }

        public Builder prepaidExpiry(Instant prepaidExpiry) {
            this.prepaidExpiry = prepaidExpiry;
            return this;
        }

        public Builder paymentMethodResponse(PaymentMethod paymentMethodResponse) {
            this.paymentMethodResponse = paymentMethodResponse;
            return this;
        }

        public PaymentInformation build() {
            return new PaymentInformation(prePaid, prePaidAmount, balance, prepaidExpiry, paymentMethodResponse);
        }
    }
}
