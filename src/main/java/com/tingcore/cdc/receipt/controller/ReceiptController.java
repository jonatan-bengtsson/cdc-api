package com.tingcore.cdc.receipt.controller;

import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.receipt.service.ReceiptService;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.payments.cpo.model.ApiReceipt;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tingcore.cdc.constant.SpringProfilesConstant.PAYMENTS_RECEIPT_V2;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + ReceiptController.VERSION + "/" + ReceiptController.RECEIPTS + "/")
@Profile(PAYMENTS_RECEIPT_V2)
public class ReceiptController {
    static final String VERSION = "v1";
    static final String SESSIONS = "sessions";
    static final String RECEIPTS = "receipts";

    private final HashIdService hashIdService;
    private final ReceiptService receiptService;

    public ReceiptController(final ReceiptService receiptService,
                             final HashIdService hashIdService) {
        this.receiptService = notNull(receiptService);
        this.hashIdService = notNull(hashIdService);
    }

    @GetMapping(SESSIONS + "/{sessionId}")
    @ApiOperation(value = "Get receipt url for a given sessionId", response = ApiReceipt.class, tags = {RECEIPTS})
    public ApiReceipt getUrlForReceiptPdf(@PathVariable("sessionId") String sessionId) {

        return hashIdService.decode(sessionId)
                .map(receiptService::getReceipt)
                .orElseThrow(() -> new EntityNotFoundException("Receipt"));
    }
}