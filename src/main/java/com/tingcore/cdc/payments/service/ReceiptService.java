package com.tingcore.cdc.payments.service;

import com.tingcore.cdc.payments.api.ApiReceipt;

public interface ReceiptService {
    ApiReceipt getReceipt(final Long sessionId);
}
