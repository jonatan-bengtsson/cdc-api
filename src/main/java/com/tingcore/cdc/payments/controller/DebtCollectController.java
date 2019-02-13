package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.payments.repository.v2.DebtCollectRepository;
import com.tingcore.commons.hash.HashIdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + DebtCollectController.VERSION + "/" + DebtCollectController.DEBT_COLLECT + "/")
public class DebtCollectController {

    static final String VERSION = "v1";
    static final String DEBT_COLLECT = "debt-collector";

    private final HashIdService hashIdService;
    private final DebtCollectRepository debtCollectRepository;

    public DebtCollectController(final HashIdService hashIdService,
                                 final DebtCollectRepository debtCollectRepository) {
        this.hashIdService = notNull(hashIdService);
        this.debtCollectRepository = notNull(debtCollectRepository);
    }

    @PostMapping("/debit/sessions/{sessionId}")
    @ApiOperation(value = "clear debt for a session", response = String.class, tags = {DEBT_COLLECT})
    public String clearDebtForSession(@PathVariable("sessionId") String sessionId) {
        return hashIdService.decode(sessionId)
                .map(debtCollectRepository::clearSession)
                .map(hashIdService::encode)
                .orElseThrow(() -> new EntityNotFoundException("SessionId"));
    }
}
