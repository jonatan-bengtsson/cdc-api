package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.exception.InputValueProcessingException;
import com.tingcore.cdc.payments.api.ApiClearSessionsRequest;
import com.tingcore.cdc.payments.repository.v2.DebtCollectRepository;
import com.tingcore.commons.hash.HashIdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
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

    @PostMapping("/sessions/{sessionId}/request-clearing")
    @ApiOperation(value = "clear debt for a session", response = String.class, tags = {DEBT_COLLECT})
    public String clearDebtForSession(@PathVariable("sessionId") String sessionId) {
        notNull(sessionId);

        return hashIdService.decode(sessionId)
                .map(id -> debtCollectRepository.clearSessions(Collections.singletonList(id)))
                .map(sessionIds -> sessionIds.get(0).value)
                .map(hashIdService::encode)
                .orElseThrow(() -> new EntityNotFoundException("SessionId"));
    }

    @PostMapping("/sessions/request-clearing")
    @ApiOperation(value = "clear debt for multiple sessions",responseContainer = "List", response = String.class, tags = {DEBT_COLLECT})
    public List<String> clearDebtForMultipleSessions(@RequestBody @NotNull ApiClearSessionsRequest request) {
        final List<Long> sessionIds = request.getSessionIds()
                .stream()
                .map(this::decodeHashId)
                .collect(toList());

         return debtCollectRepository.clearSessions(sessionIds)
                .stream()
                .map(sessionId -> hashIdService.encode(sessionId.value))
                .collect(toList());
    }

    private Long decodeHashId(String hashId) {
        return hashIdService.decode(hashId)
                .orElseThrow(() -> new InputValueProcessingException(hashId));
    }
}
