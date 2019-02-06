package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.payments.sessionstasher.models.v1.Amount;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static org.apache.commons.lang3.Validate.notNull;

public class ApiAmountAdapter implements ApiAmount {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00"); // should rather be localized by the client
    static {
        final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols();
        DECIMAL_FORMAT_SYMBOLS.setDecimalSeparator(',');
        DECIMAL_FORMAT.setDecimalFormatSymbols(DECIMAL_FORMAT_SYMBOLS);
    }

    private final Amount amount;

    public ApiAmountAdapter(final Amount amount) {
        this.amount = notNull(amount);
    }

    @Override
    public String getInclVat() {
        return DECIMAL_FORMAT.format(amount.getAmountMinorUnitsIncl());
    }

    @Override
    public String getExclVat() {
        return DECIMAL_FORMAT.format(amount.getAmountMinorUnitsExcl());
    }

    @Override
    public String getCurrency() {
        return amount.getCurrency();
    }

    @Override
    public String getVat() {
        return Double.toString(amount.getVat());
    }
}
