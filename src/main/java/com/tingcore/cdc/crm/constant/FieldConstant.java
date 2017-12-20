package com.tingcore.cdc.crm.constant;

/**
 * Field names are according to Google API Design Guide
 *
 * @author palmithor
 * @see <a href="https://cloud.google.com/apis/design/">API Design Guide</a>
 * @since 2017-09-04
 */
public class FieldConstant {

    public static final String ID = "id";

    public static final String CREATED = "created";
    public static final String UPDATED = "updated";
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String KEY_IDENTIFIER = "keyIdentifier";
    public static final String VALID_TO = "validTo";
    public static final String VALID_FROM = "validFrom";
    public static final String BOOKKEEPING_ACCOUNT_ID = "bookkeepingAccountId";
    public static final String PAYMENT_OPTION_REFERENCE = "paymentOptionReference";
    public static final String USER_PAYMENT_OPTIONS = "userPaymentOptions";
    public static final String IS_ENABLED = "isEnabled";
    public static final String PAYMENT_OPTION = "paymentOption";

    private FieldConstant() {
    }
}