package com.tingcore.cdc.sessionhistory.controller;

import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.exception.SessionHistoryFailureException;
import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.sessionhistory.service.SessionHistoryService;
import com.tingcore.commons.hash.HashIdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + SessionHistoryController.VERSION + "/" + SessionHistoryController.SESSION_HISTORY)
public class SessionHistoryController {
    static final String VERSION = "v1";
    static final String SESSION_HISTORY = "session-history";
    private static final String CUSTOMER_KEY = "customer-key";

    private final SessionHistoryService sessionHistoryService;
    private HashIdService hashIdService;

    public SessionHistoryController(final SessionHistoryService sessionHistoryService,
                                    final HashIdService hashIdService) {
        this.sessionHistoryService = notNull(sessionHistoryService);
        this.hashIdService = notNull(hashIdService);
    }

    @GetMapping(CUSTOMER_KEY + "/{customerKeyId}")
    @ApiOperation(value = "Get Session history for a users customer key between two dates", response = ApiChargeHistory.class, responseContainer = "List", tags = {SwaggerDocConstants.TAGS_SESSION_HISTORY})
    public List<ApiChargeHistory> getEmpSessionHistory(@PathVariable("customerKeyId") @NotNull String customerKeyId,
                                                       @RequestParam("fromInstant") @NotNull Long from,
                                                       @RequestParam("toInstant") @NotNull Long to) {
        return hashIdService.decode(customerKeyId)
                .map(id -> sessionHistoryService.getSessionHistory(id, from, to))
                .orElseThrow(() -> new SessionHistoryFailureException("Could not get session history"));
    }
}
