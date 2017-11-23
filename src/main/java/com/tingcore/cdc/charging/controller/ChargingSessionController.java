package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.api.ChargingSession;
import com.tingcore.cdc.charging.api.ChargingSessionStatus;
import com.tingcore.cdc.charging.api.CreateChargingSessionRequest;
import com.tingcore.cdc.charging.api.CustomerKey;
import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.ChargingSessionId;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.exception.NoSessionFoundException;
import com.tingcore.cdc.model.UserReference;
import com.tingcore.commons.api.service.HashIdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.tingcore.cdc.charging.controller.ChargingSessionController.SESSIONS;
import static com.tingcore.cdc.charging.controller.ChargingSessionController.VERSION;
import static org.apache.commons.lang3.Validate.notNull;

@Api
@RestController
@RequestMapping("/" + VERSION + "/" + SESSIONS)
public class ChargingSessionController {
    static final String VERSION = "v1";
    static final String SESSIONS = "chargingSessions";

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
    @ApiOperation(code = 201, value = "Create a charging session", response = ChargingSession.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Charge site not found.", response = Error.class)
    })
    public ResponseEntity<ChargingSession> createSession(final @RequestBody @Valid CreateChargingSessionRequest request) {
        // TODO return created response
        return ResponseEntity.ok(toApiObject(chargingSessionService.startSession(
                new UserReference(Long.toString(authorizedUser.getUser().getId())),
                customerKeyIdFromRequest(request),
                chargePointIdFromRequest(request),
                connectorIdFromRequest(request)
        )));
    }

    @GetMapping(value = "/{chargingSessionId}")
    @ApiOperation(value = "Get a charge session.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Get a charging session", response = ChargingSession.class),
	@ApiResponse(code = 404, message = "Charging session not found", response = Error.class)
    })
    public ResponseEntity<ChargingSession> getChargeSession(@PathVariable("chargingSessionId") String chargingSessionId) {

      try {
        Long sessionId =
            hashIdService.decode(chargingSessionId).get();

        ChargingSession chargingSession =
            toApiObject(chargingSessionService.fetchSession(new ChargingSessionId(sessionId)));

        return ResponseEntity.ok(chargingSession);

      } catch (NoSessionFoundException e) {
          return ResponseEntity.notFound().build();
      }
    }

    private CustomerKeyId customerKeyIdFromRequest(final CreateChargingSessionRequest request) {
        return new CustomerKeyId(698L);
        /*return hashIdService.decode(request.getCustomerKey())
                .map(CustomerKeyId::new)
                .orElseThrow(() -> new EntityNotFoundException("Could not find the specified customer key."));*/
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
                new CustomerKey(hashIdService.encode(chargingSession.customerKeyId.value), "********"), // TODO lookup value and mask
                chargingSession.startTime,
                chargingSession.endTime,
                ChargingSessionStatus.valueOf(chargingSession.status.name())
        );
    }
}
