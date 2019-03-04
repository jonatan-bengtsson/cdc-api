package com.tingcore.cdc.payments.service.v2;

import com.tingcore.cdc.payments.api.ApiReceipt;
import com.tingcore.cdc.payments.repository.v2.ReceiptRepository;
import com.tingcore.cdc.payments.service.ReceiptService;
import org.springframework.stereotype.Service;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(final ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public ApiReceipt getReceipt(final Long sessionId) {
        ApiReceipt receipt = new ApiReceipt();
        receipt.setUrl(receiptRepository.getReceipt(sessionId));
        return receipt;
    }
}
