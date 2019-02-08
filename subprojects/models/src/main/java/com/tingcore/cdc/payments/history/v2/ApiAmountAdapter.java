package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.payments.sessionstasher.models.v1.Amount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiAmountAdapter implements ApiAmount {
    private static final Locale LOCALE = new Locale("sv","SE");
    private static final DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(LOCALE);
    private static final NumberFormat vatFormat = NumberFormat.getInstance(LOCALE);
    static {
        DecimalFormatSymbols symbols = numberFormat.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        numberFormat.setDecimalFormatSymbols(symbols);
    }

    private final Amount amount;
    private final int fractionDigits;

    public ApiAmountAdapter(final Amount amount) {
        this.amount = notNull(amount);
        this.fractionDigits = Currency.getInstance(amount.getCurrency()).getDefaultFractionDigits();
    }

    @Override
    public String getInclVat() {
        return format(amount.getAmountMinorUnitsIncl(), fractionDigits);
    }

    @Override
    public String getExclVat() {
        return format(amount.getAmountMinorUnitsExcl(), fractionDigits);
    }

    @Override
    public String getCurrency() {
        return amount.getCurrency();
    }

    @Override
    public String getVat() {
        return vatFormat.format(amount.getVat());
    }

    private static String format(final long amount, final int fractionDigits) {
        double d = convertAmount(amount, fractionDigits);
        return numberFormat.format(d).trim();
    }

    private static double convertAmount(final long amount, final int fractionDigits) {
        return BigDecimal.valueOf(amount)
                .movePointLeft(fractionDigits)
                .doubleValue();
    }
}
