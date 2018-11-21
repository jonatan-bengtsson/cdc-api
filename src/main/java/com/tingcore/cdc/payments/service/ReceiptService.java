package com.tingcore.cdc.payments.service;

import com.tingcore.payments.cpo.model.ApiReceipt;

public interface ReceiptService {
    ApiReceipt getReceipt(final Long sessionId);
}
