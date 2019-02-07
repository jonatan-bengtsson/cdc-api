package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.payments.sessionstasher.models.v1.Amount;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiAmountAdapter implements ApiAmount {
    private static final Locale LOCALE = new Locale("sv","SE");
    private static final NumberFormat NUMBER_FORMAT = DecimalFormat.getNumberInstance(LOCALE);

    private final Amount amount;
    private final int fractionDigits;

    public ApiAmountAdapter(final Amount amount) {
        this.amount = notNull(amount);
        this.fractionDigits = Currency.getInstance(amount.getCurrency()).getDefaultFractionDigits();
    }

    @Override
    public String getInclVat() {
        double a = convertAmount(amount.getAmountMinorUnitsIncl(), fractionDigits);
        return formatNumber(a, LOCALE, fractionDigits);
    }

    @Override
    public String getExclVat() {
        double a = convertAmount(amount.getAmountMinorUnitsExcl(), fractionDigits);
        return formatNumber(a, LOCALE, fractionDigits);
    }

    @Override
    public String getCurrency() {
        return amount.getCurrency();
    }

    @Override
    public String getVat() {
        return NUMBER_FORMAT.format(amount.getVat());
    }

    private static double convertAmount(final long amount, final int fractionDigits) {
        return amount / (Math.pow(10, fractionDigits));
    }

    private static String formatNumber(final double amount, final Locale locale, final int fractionDigits) {
        return String.format(locale, "%." + fractionDigits + "f", amount);
    }
}
