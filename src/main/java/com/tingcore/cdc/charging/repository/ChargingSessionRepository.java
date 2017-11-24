package com.tingcore.cdc.charging.repository;

import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.exception.NoSessionFoundException;
import com.tingcore.payments.emp.api.ChargesApi;
import com.tingcore.payments.emp.model.*;
import org.springframework.stereotype.Repository;
import retrofit2.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class ChargingSessionRepository {
    private final ChargesApi chargesApi;

    public ChargingSessionRepository(final ChargesApi chargesApi) {
        this.chargesApi = notNull(chargesApi);
    }

    public ChargingSession fetchSession(final ChargingSessionId id) {
        try {
            Response<ApiCharge> response = chargesApi.getCharge(id.value).execute();
            if (response.code() == 404) {
                throw new NoSessionFoundException("Session not found");
            }
            return apiSessionToModel(response.body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public ChargingSession createSession(final CustomerKeyId targetUser) {
        try {
            final CreateChargeRequest createChargeRequest = new CreateChargeRequest();
            createChargeRequest.setUser(targetUser.value);
            final Response<ApiCharge> charge = chargesApi.createCharge(createChargeRequest).execute();
            if (!charge.isSuccessful()) {
                // TODO: handle errors
                return null;
            }
            return apiSessionToModel(charge.body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
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
            return apiEventToModel(sessionId, chargesApi.createChargeEvent(sessionId.value, startEvent).execute().body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
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
            return apiEventToModel(sessionId, chargesApi.createChargeEvent(sessionId.value, stopEvent).execute().body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static ChargingSession apiSessionToModel(final ApiCharge apiCharge) {
        return new ChargingSession(
                new ChargingSessionId(apiCharge.getId()),
                new CustomerKeyId(apiCharge.getUser()),
                apiTimeToNullableInstant(apiCharge.getStartTime()),
                apiTimeToNullableInstant(apiCharge.getStopTime()),
                apiSessionStateToModel(apiCharge.getState())
        );
    }

    private static ChargingSessionStatus apiSessionStateToModel(final ApiCharge.StateEnum state) {
        switch (state) {
            case CREATED:
                return ChargingSessionStatus.CREATED;
            case WAITING_TO_START:
                return ChargingSessionStatus.WAITING_TO_START;
            case STARTED:
                return ChargingSessionStatus.STARTED;
            case WAITING_TO_COMPLETE:
                return ChargingSessionStatus.WAITING_TO_COMPLETE;
            case COMPLETED:
                return ChargingSessionStatus.COMPLETED;
            case TRANSACTION_CLEARED:
                return ChargingSessionStatus.CLEARED;
            case TIMEOUT_WAITING_TO_START:
            case FAILED_TO_START:
            case TIMEOUT_WAITING_TO_COMPLETE:
            case FAILED_TO_COMPLETE:
            case FAILED_TO_CLEAR_TRANSACTION:
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