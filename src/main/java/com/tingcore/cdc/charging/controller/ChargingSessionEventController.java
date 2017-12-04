package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.api.AddChargingSessionEventRequest;
import com.tingcore.cdc.charging.api.ChargingSessionEvent;
import com.tingcore.cdc.charging.api.ChargingSessionEventNature;
import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.ChargingSessionId;
import com.tingcore.cdc.charging.model.TrustedUserId;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.api.service.HashIdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.tingcore.cdc.charging.controller.ChargingSessionEventController.SESSIONS;
import static com.tingcore.cdc.charging.controller.ChargingSessionEventController.VERSION;
import static org.apache.commons.lang3.Validate.notNull;

@Api
@RestController
@RequestMapping("/" + VERSION + "/" + SESSIONS)
public class ChargingSessionEventController {
    static final String VERSION = "v1";
    static final String SESSIONS = ChargingSessionController.SESSIONS;
    static final String EVENTS = "events";

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    private final HashIdService hashIdService;
    private final ChargingSessionService chargingSessionService;

    public ChargingSessionEventController(final HashIdService hashIdService,
                                          final ChargingSessionService chargingSessionService) {
        this.hashIdService = notNull(hashIdService);
        this.chargingSessionService = notNull(chargingSessionService);
    }

    @PostMapping("/{chargingSessionId}/" + EVENTS)
    @ApiOperation(code = 201, value = "Create a session event.", response = ChargingSessionEvent.class, tags = {SESSIONS})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "The session with the supplied id could not be found.", response = Error.class)
    })
    public ResponseEntity<ChargingSessionEvent> addEvent(final @PathVariable("chargingSessionId") String chargingSessionId,
                                                         final @RequestBody @Valid AddChargingSessionEventRequest request) {
        switch (request.nature) {
            case STOP_REQUESTED:
                return handleStopEvent(chargingSessionId, String.class.cast(request.data.get("chargePointId")));
        }
        throw new IllegalArgumentException("Only stop events are supported for now.");
    }

    private ResponseEntity<ChargingSessionEvent> handleStopEvent(final String sessionId,
                                                                 final String chargePointId) {
        // TODO return created response
        return ResponseEntity.ok(toApiObject(chargingSessionService.stopSession(
                new TrustedUserId(authorizedUser.getUser().getId()),
                sessionIdFromRequest(sessionId),
                chargePointIdFromRequest(chargePointId)
        )));
    }

    private ChargingSessionEvent toApiObject(final com.tingcore.cdc.charging.model.ChargingSessionEvent chargingSessionEvent) {
        return new ChargingSessionEvent(
                hashIdService.encode(chargingSessionEvent.sessionId.value),
                hashIdService.encode(chargingSessionEvent.eventId.value),
                chargingSessionEvent.time,
                ChargingSessionEventNature.valueOf(chargingSessionEvent.nature.name())
        );
    }

    private ChargingSessionId sessionIdFromRequest(final String sessionId) {
        return hashIdService.decode(sessionId)
                .map(ChargingSessionId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified session."));
    }

    private ChargePointId chargePointIdFromRequest(final String chargePointId) {
        return hashIdService.decode(chargePointId)
                .map(ChargePointId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified charge point."));
    }
}
