package com.tingcore.cdc.charging.repository;

import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.exception.NoSessionFoundException;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.model.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.time.Instant;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Repository
public class ChargingSessionRepository {
    private final ChargesApi chargesApi;

    public ChargingSessionRepository(final ChargesApi chargesApi) {
        this.chargesApi = notNull(chargesApi);
    }

    public ChargingSession fetchSession(final ChargingSessionId id) {
        try {
            final ApiCharge charge = chargesApi.getCharge(id.value);
            return apiSessionToModel(charge);
        } catch (final RestClientException exception) {
            if (exception instanceof HttpStatusCodeException) {
                final HttpStatusCodeException statusCodeException = HttpStatusCodeException.class.cast(exception);
                if (statusCodeException.getStatusCode().equals(NOT_FOUND)) {
                    throw new NoSessionFoundException("Session not found");
                }
            }
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    public ChargingSession createSession(final TrustedUserId trustedUserId,
                                         final CustomerKeyId targetUser) {
        try {
            final CreateChargeRequest createChargeRequest = new CreateChargeRequest();
            createChargeRequest.setUser(trustedUserId.value);
            createChargeRequest.setAccount(targetUser.value);
            final ApiCharge charge = chargesApi.createCharge(createChargeRequest);
            return apiSessionToModel(charge);
        } catch (final RestClientException exception) {
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    public ChargingSessionEvent startSession(final ChargingSessionId sessionId,
                                             final AuthorizationToken token,
                                             final ChargePointId chargePointId,
                                             final ConnectorId connectorId) {
        try {
            final CreateChargeEventRequest startEvent = new CreateChargeEventRequest();
            final Event event = new Event();
            event.setNature(Event.NatureEnum.START_REQUESTED);
            event.setData(ImmutableMap.of(
                    "authorization", token.value,
                    "chargePoint", chargePointId.value,
                    "connector", connectorId.value
            ));
            startEvent.setEvent(event);
            return apiEventToModel(sessionId, chargesApi.createChargeEvent(sessionId.value, startEvent));
        } catch (final RestClientException exception) {
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    public ChargingSessionEvent stopSession(final ChargingSessionId sessionId,
                                            final AuthorizationToken token,
                                            final ChargePointId chargePointId) {
        try {
            final CreateChargeEventRequest stopEvent = new CreateChargeEventRequest();
            final Event event = new Event();
            event.setNature(Event.NatureEnum.STOP_REQUESTED);
            event.setData(ImmutableMap.of(
                    "authorization", token.value,
                    "chargePoint", chargePointId.value
            ));
            stopEvent.setEvent(event);
            return apiEventToModel(sessionId, chargesApi.createChargeEvent(sessionId.value, stopEvent));
        } catch (final RestClientException exception) {
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    static ChargingSession apiSessionToModel(final ApiCharge apiCharge) {
        return new ChargingSession(
                new ChargingSessionId(apiCharge.getId()),
                new CustomerKeyId(apiCharge.getAccount()),
                apiSessionPriceToModel(apiCharge.getPrice()),
                apiTimeToNullableInstant(apiCharge.getStartTime()),
                apiTimeToNullableInstant(apiCharge.getStopTime()),
                apiSessionStateToModel(apiCharge.getState())
        );
    }

    private static Price apiSessionPriceToModel(final ApiAmount apiPrice) {
        return Optional.ofNullable(apiPrice).map(price -> new Price(
                price.getExclVat(),
                price.getInclVat(),
                price.getCurrency()
        )).orElse(null);
    }

    private static ChargingSessionStatus apiSessionStateToModel(final ApiCharge.StateEnum state) {
        switch (state) {
            case CREATED:
                return ChargingSessionStatus.CREATED;
            case WAITING_TO_START:
                return ChargingSessionStatus.WAITING_TO_START;
            case STARTED:
                return ChargingSessionStatus.STARTED;
            case WAITING_TO_STOP:
                return ChargingSessionStatus.WAITING_TO_STOP;
            case STOPPED:
                return ChargingSessionStatus.STOPPED;
            case PRICE_ESTABLISHED:
                return ChargingSessionStatus.COMPLETE;
            case TIMEOUT_WAITING_TO_START:
            case TIMEOUT_WAITING_TO_STOP:
            case FAILED_TO_START:
            case FAILED_TO_STOP:
            case FAILED_TO_ESTABLISH_PRICE:
            case FAILED:
                return ChargingSessionStatus.FAILED;
        }
        throw new IllegalStateException(format("No explicit mapping of charging session state %s was found.", state.name()));
    }

    private ChargingSessionEvent apiEventToModel(final ChargingSessionId sessionId,
                                                 final ApiChargeEvent apiEvent) {
        return new ChargingSessionEvent(
                sessionId,
                new ChargingSessionEventId(apiEvent.getId()),
                apiTimeToNullableInstant(apiEvent.getTime()),
                ChargingSessionEventNature.valueOf(apiEvent.getNature().name())
        );
    }

    private static Instant apiTimeToNullableInstant(final Long time) {
        return ofNullable(time).map(Instant::ofEpochMilli).orElse(null);
    }
}