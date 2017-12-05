package com.tingcore.cdc.crm.response;

import java.time.Instant;

/**
 * TODO this might completely change
 *
 * @author palmithor
 * @since 2017-11-10
 */
public class PaymentInformationResponse {

    private final Boolean prePaid;
    private final Integer prePaidAmount;
    private final Integer balance;
    private final Instant prepaidExpiry;
    private final PaymentMethodResponse paymentMethodResponseResponse;

    public PaymentInformationResponse() {
        this.prePaid = null;
        this.prePaidAmount = null;
        this.balance = null;
        this.prepaidExpiry = null;
        this.paymentMethodResponseResponse = null;
    }

    public PaymentInformationResponse(final Boolean prePaid,
                                      final Integer prePaidAmount,
                                      final Integer balance,
                                      final Instant prepaidExpiry,
                                      final PaymentMethodResponse paymentMethodResponseResponse) {
        this.prePaid = prePaid;
        this.prePaidAmount = prePaidAmount;
        this.balance = balance;
        this.prepaidExpiry = prepaidExpiry;
        this.paymentMethodResponseResponse = paymentMethodResponseResponse;
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

    public PaymentMethodResponse getPaymentMethodResponseResponse() {
        return paymentMethodResponseResponse;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean prePaid;
        private Integer prePaidAmount;
        private Integer balance;
        private Instant prepaidExpiry;

        private PaymentMethodResponse paymentMethodResponseResponse;

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

        public Builder paymentMethodResponse(PaymentMethodResponse paymentMethodResponseResponse) {
            this.paymentMethodResponseResponse = paymentMethodResponseResponse;
            return this;
        }

        public PaymentInformationResponse build() {
            return new PaymentInformationResponse(prePaid, prePaidAmount, balance, prepaidExpiry, paymentMethodResponseResponse);
        }
    }
}