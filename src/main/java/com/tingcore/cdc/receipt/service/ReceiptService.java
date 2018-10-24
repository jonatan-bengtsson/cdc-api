package com.tingcore.cdc.receipt.service;

import com.tingcore.cdc.receipt.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(final ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public String getReceipt(final Long sessionId) {
        return receiptRepository.getReceipt(sessionId);
    }
}