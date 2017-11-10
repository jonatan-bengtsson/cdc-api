package com.tingcore.cdc.crm.response;

/**
 * @author palmithor
 * @since 2017-11-10
 */
public class PaymentMethodResponse {

    private final String type;
    private final String cardNumber;
    private final String expiryDate;
    private final String cardholder;

    public PaymentMethodResponse() {
        this.type = null;
        this.cardNumber = null;
        this.expiryDate = null;
        this.cardholder = null;
    }

    public PaymentMethodResponse(final String type, final String cardNumber, final String expiryDate, final String cardholder) {
        this.type = type;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardholder = cardholder;
    }

    public String getType() {
        return type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCardholder() {
        return cardholder;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String type;
        private String cardNumber;
        private String expiryDate;

        private String cardholder;

        private Builder() {
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder expiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder cardholder(String cardholder) {
            this.cardholder = cardholder;
            return this;
        }

        public PaymentMethodResponse build() {
            return new PaymentMethodResponse(type, cardNumber, expiryDate, cardholder);
        }
    }
}
