package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.payments.model.ChargingSession;
import com.tingcore.cdc.payments.model.ChargingSessionStatus;
import com.tingcore.cdc.payments.model.CreateChargingSessionRequest;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static com.tingcore.cdc.charging.controller.ChargingSessionController.SESSIONS;
import static com.tingcore.cdc.charging.controller.ChargingSessionController.VERSION;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Api
@RestController
@RequestMapping("/" + VERSION + "/" + SESSIONS)
public class ChargingSessionController {
    static final String VERSION = "v1";
    static final String SESSIONS = "charging-sessions";

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    private final HashIdService hashIdService;
    private final ChargingSessionService chargingSessionService;

    public ChargingSessionController(final HashIdService hashIdService,
                                     final ChargingSessionService chargingSessionService) {
        this.hashIdService = notNull(hashIdService);
        this.chargingSessionService = notNull(chargingSessionService);
    }

    @PostMapping
    @ApiOperation(code = 201, value = "Create a charging session", response = ChargingSession.class, tags = {SwaggerDocConstants.TAGS_CHARGING_SESSIONS})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Charge site not found.", response = Error.class)
    })
    public ResponseEntity<ChargingSession> createChargingSession(final @RequestBody @Valid CreateChargingSessionRequest request) {
        // TODO return created response
        return ResponseEntity.ok(toApiObject(chargingSessionService.startSession(
                new TrustedUserId(authorizedUser.getId()),
                customerKeyIdFromRequest(request),
                chargePointIdFromRequest(request),
                connectorIdFromRequest(request)
        )));
    }

    @GetMapping(value = "/{chargingSessionId}")
    @ApiOperation(value = "Get a charging session.", tags = {SwaggerDocConstants.TAGS_CHARGING_SESSIONS})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get a charging session", response = ChargingSession.class),
            @ApiResponse(code = 404, message = "Charging session not found", response = ErrorResponse.class)
    })
    public ResponseEntity<ChargingSession> getChargingSession(final @PathVariable("chargingSessionId") String chargingSessionId) {
            return ResponseEntity.ok(toApiObject(chargingSessionService.fetchSession(sessionIdFromHash(chargingSessionId))));
    }

    @GetMapping
    @ApiOperation(value = "Get ongoing charging sessions.", tags = {SwaggerDocConstants.TAGS_CHARGING_SESSIONS})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get ongoing charging sessions.", response = ChargingSession.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Charging sessions not found", response = ErrorResponse.class)
    })
    public ResponseEntity getOngoingChargingSessions() {
        return ResponseEntity.ok(chargingSessionService.fetchOngoingSessions(new TrustedUserId(authorizedUser.getId()))
        .stream()
        .map(this::toApiObject)
        .collect(toList()));
    }

    private ChargingSessionId sessionIdFromHash(final String sessionIdHash) {
        return hashIdService.decode(sessionIdHash)
                .map(ChargingSessionId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified session id."));
    }

    private CustomerKeyId customerKeyIdFromRequest(final CreateChargingSessionRequest request) {
        return hashIdService.decode(request.getCustomerKey())
                .map(CustomerKeyId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified customer key."));
    }

    private ChargePointId chargePointIdFromRequest(final CreateChargingSessionRequest request) {
        return hashIdService.decode(request.getChargePointId())
                .map(ChargePointId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified charge point."));
    }

    private ConnectorId connectorIdFromRequest(final CreateChargingSessionRequest request) {
        return hashIdService.decode(request.getConnectorId())
                .map(ConnectorId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified connector."));
    }

    private ChargingSession toApiObject(final com.tingcore.cdc.charging.model.ChargingSession chargingSession) {
        return new ChargingSession(
                hashIdService.encode(chargingSession.id.value),
                chargingSession.customerKeyId.value,
                toApiObject(chargingSession.price),
                chargingSession.startTime,
                chargingSession.endTime,
                ChargingSessionStatus.valueOf(chargingSession.status.name()),
                Optional.ofNullable(chargingSession.connectorId).map(connectorId -> connectorId.id).orElse(null),
                Optional.ofNullable(chargingSession.chargePointId).map(chargePointId -> chargePointId.id).orElse(null),
                Optional.ofNullable(chargingSession.chargeSiteId).map(chargeSiteId -> chargeSiteId.id).orElse(null));
    }

    private com.tingcore.cdc.payments.model.Price toApiObject(final Price price) {
        return Optional.ofNullable(price).map(p -> new com.tingcore.cdc.payments.model.Price(
                p.inclVat,
                p.exclVat,
                p.currency
        )).orElse(null);
    }
}
