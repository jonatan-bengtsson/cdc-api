package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.payments.cpo.api.ChargesApi;
import com.tingcore.payments.cpo.model.*;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class ChargingSessionRepository extends AbstractApiRepository {
    private final ChargesApi chargesApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public ChargingSessionRepository(final ObjectMapper objectMapper,
                                     final ChargesApi chargesApi) {
        super(notNull(objectMapper));
        this.chargesApi = notNull(chargesApi);
    }

    public ChargingSessionBuilder fetchSession(final ChargingSessionId id) {
        final ApiCharge charge = getResponseOrChargingSessionError(chargesApi.getCharge(id.value));
        return apiSessionToModel(charge);
    }

    public List<ChargingSessionBuilder> fetchSessionsForUserId(final TrustedUserId id) {
        return getResponseOrChargingSessionError(chargesApi.getOngoingCharges(id.value))
                .stream()
                .map(ChargingSessionRepository::apiSessionToModel)
                .collect(Collectors.toList());
    }

    public ChargingSessionBuilder createSession(final TrustedUserId trustedUserId,
                                                final CustomerKeyId targetUser) {
        final CreateChargeRequest createChargeRequest = new CreateChargeRequest();
        createChargeRequest.setUser(trustedUserId.value);
        createChargeRequest.setCustomerKey(targetUser.value);
        final ApiCharge charge = getResponseOrChargingSessionError(chargesApi.createCharge(createChargeRequest));
        return apiSessionToModel(charge);
    }

    public ChargingSessionEvent startSession(final ChargingSessionId sessionId,
                                             final AuthorizationToken token,
                                             final ChargePointId chargePointId,
                                             final ConnectorId connectorId) {
        final CreateChargeEventRequest startEvent = new CreateChargeEventRequest();
        final Event event = new Event();
        event.setNature(Event.NatureEnum.START_REQUESTED);
        event.setData(ImmutableMap.of(
                "authorization", token.value,
                "chargePoint", chargePointId.id,
                "connector", connectorId.id
        ));
        startEvent.setEvent(event);
        return apiEventToModel(sessionId, getResponseOrChargingSessionError(chargesApi.createChargeEvent(sessionId.value, startEvent)));
    }

    public ChargingSessionEvent stopSession(final ChargingSessionId sessionId,
                                            final AuthorizationToken token,
                                            final ChargePointId chargePointId) {
        final CreateChargeEventRequest stopEvent = new CreateChargeEventRequest();
        final Event event = new Event();
        event.setNature(Event.NatureEnum.STOP_REQUESTED);
        event.setData(ImmutableMap.of(
                "authorization", token.value,
                "chargePoint", chargePointId.id
        ));
        stopEvent.setEvent(event);
        return apiEventToModel(sessionId, getResponseOrChargingSessionError(chargesApi.createChargeEvent(sessionId.value, stopEvent)));
    }

    static ChargingSessionBuilder apiSessionToModel(final ApiCharge apiCharge) {
        return new ChargingSessionBuilder().setId(new ChargingSessionId(apiCharge.getId()))
                .setCustomerKeyId(new CustomerKeyId(apiCharge.getCustomerKey()))
                .setPrice(apiSessionPriceToModel(apiCharge.getPrice()))
                .setStartTime(apiTimeToNullableInstant(apiCharge.getStartTime()))
                .setEndTime(apiTimeToNullableInstant(apiCharge.getStopTime()))
                .setStatus(apiSessionStateToModel(apiCharge.getState()))
                .setConnectorId(apiCharge.getConnectorId() != null ? new ConnectorId(apiCharge.getConnectorId()) : null);
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

    private <T, E extends ExternalApiException> T getResponseOrChargingSessionError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), ChargingSessionApiException::new);
    }

    private static Instant apiTimeToNullableInstant(final Long time) {
        return ofNullable(time).map(Instant::ofEpochMilli).orElse(null);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
