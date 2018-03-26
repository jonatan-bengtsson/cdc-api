package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.exception.SessionHistoryFailureException;
import com.tingcore.cdc.payments.service.SessionHistoryService;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.payments.cpo.model.ApiChargeHistory;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + SessionHistoryController.VERSION + "/" + SessionHistoryController.SESSION_HISTORY)
public class SessionHistoryController {
    static final String VERSION = "v1";
    static final String SESSION_HISTORY = "sessionhistory";
    static final String CUSTOMER_KEY = "customerkey";

    private final SessionHistoryService sessionHistoryService;
    private HashIdService hashIdService;

    public SessionHistoryController(final SessionHistoryService sessionHistoryService,
                                    final HashIdService hashIdService) {
        this.sessionHistoryService = notNull(sessionHistoryService);
        this.hashIdService = notNull(hashIdService);
    }

    @GetMapping(CUSTOMER_KEY + "/{customerkeyid}")
    @ApiOperation(value = "Get Session history for a users customer key between two dates", response = ApiChargeHistory.class, responseContainer = "List", tags = {SESSION_HISTORY})
    public List<ApiChargeHistory> getEmpSessionHistory(@PathVariable("customerkeyid") @NotNull String customerKeyId,
                                                       @RequestParam("frominstant") @NotNull Long from,
                                                       @RequestParam("toinstant") @NotNull Long to) {
        return hashIdService.decode(customerKeyId)
                .map(id -> sessionHistoryService.getSessionHistory(id, from, to))
        .orElseThrow(() -> new SessionHistoryFailureException("Could not get session history"));


    }
}
