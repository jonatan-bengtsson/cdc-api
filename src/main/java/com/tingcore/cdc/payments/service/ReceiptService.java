package com.tingcore.cdc.payments.service;

import com.tingcore.cdc.payments.repository.ReceiptRepository;
import com.tingcore.payments.cpo.model.ApiReceipt;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.tingcore.cdc.constant.SpringProfilesConstant.PAYMENTS_RECEIPT_V2;

@Service
@Profile("!" + PAYMENTS_RECEIPT_V2)
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(final ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public ApiReceipt getReceipt(final Long sessionId) {
        return receiptRepository.getReceipt(sessionId);
    }
}
