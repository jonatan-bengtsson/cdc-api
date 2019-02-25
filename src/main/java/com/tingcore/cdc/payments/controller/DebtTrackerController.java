package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.exception.InputValueProcessingException;
import com.tingcore.cdc.payments.api.ApiSessionDebt;
import com.tingcore.cdc.payments.repository.v2.DebtTrackerRepository;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.payments.debttracker.response.ApiDebtSummary;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + DebtTrackerController.VERSION + "/" + DebtTrackerController.DEBT_TRACKER + "/")
public class DebtTrackerController {
    static final String VERSION = "v1";
    static final String DEBT_TRACKER = "debt-tracker";

    private final HashIdService hashIdService;
    private final DebtTrackerRepository debtTrackerRepository;

    public DebtTrackerController(final HashIdService hashIdService,
                                 final DebtTrackerRepository debtTrackerRepository) {
        this.hashIdService = notNull(hashIdService);
        this.debtTrackerRepository = notNull(debtTrackerRepository);
    }

    @GetMapping("/users/{userId}/charging-keys/{chargingKeyId}")
    @ApiOperation(value = "Get all debt for a specified user and customer key", tags = {DEBT_TRACKER})
    public List<ApiSessionDebt> getAllDebtForUserAndChargingKeyId(@PathVariable("userId") String userId,
                                                                  @PathVariable("chargingKeyId") String chargingKeyId) {
        notNull(userId);
        notNull(chargingKeyId);

        final Long user = decodeHashId(userId);
        final Long key = decodeHashId(chargingKeyId);

        return debtTrackerRepository.getAllDebtForUserAndChargingKeyId(user, key)
                .stream()
                .map(this::toDomainSessionDebt)
                .collect(toList());
    }

    @GetMapping("/users/{userId}/charging-keys/{chargingKeyId}/summary")
    @ApiOperation(value = "Get a debt summary for a specified user and customer key", tags = {DEBT_TRACKER})
    public List<ApiDebtSummary> getDebtSummaryForUserAndChargingKeyId(@PathVariable("userId") String userId,
                                                                      @PathVariable("chargingKeyId") String chargingKeyId) {
        notNull(userId);
        notNull(chargingKeyId);

        final Long user = decodeHashId(userId);
        final Long key = decodeHashId(chargingKeyId);

        return debtTrackerRepository.getDebtSummaryForUserAndChargingKeyId(user, key);
    }

    private ApiSessionDebt toDomainSessionDebt(com.tingcore.payments.debttracker.response.ApiSessionDebt debt) {
        final String sessionId = hashIdService.encode(debt.getSessionId());
        return new ApiSessionDebt(sessionId, debt.getCurrency(), debt.getMinorUnitsInclVat(), debt.getTimestamp(), debt.getVatPercent());
    }

    private Long decodeHashId(String hashId) {
        return hashIdService.decode(hashId)
                .orElseThrow(() -> new InputValueProcessingException(hashId));
    }
}
