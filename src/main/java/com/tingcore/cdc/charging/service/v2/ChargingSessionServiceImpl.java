package com.tingcore.cdc.charging.service.v2;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.AssetPaymentsRepository;
import com.tingcore.cdc.charging.repository.SessionsRepository;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.charging.assets.model.ChargePointInfo;
import com.tingcore.payments.api.SessionsApi;
import com.tingcore.payments.model.ApiSessionId;
import com.tingcore.payments.model.RemoteStartRequestV1;
import com.tingcore.payments.model.RemoteStopRequestV1;
import com.tingcore.sessions.api.v1.ApiAmount;
import com.tingcore.sessions.api.v1.ApiSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.charging.model.ChargingSessionStatus.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private final SessionsApi sessionsApi;
    private final AssetPaymentsRepository assetPaymentsRepository;
    private final SessionsRepository sessionsRepository;

    public ChargingSessionServiceImpl(SessionsApi sessionsApi,
                                      AssetPaymentsRepository assetPaymentsRepository,
                                      SessionsRepository sessionsRepository) {
        this.sessionsApi = notNull(sessionsApi);
        this.assetPaymentsRepository = notNull(assetPaymentsRepository);
        this.sessionsRepository = notNull(sessionsRepository);
    }

    @Override
    public ChargingSession startSession(TrustedUserId trustedUserId, CustomerKeyId customerKeyId,
                                        ChargePointId chargePointId, ConnectorId connectorId) {
        CompletableFuture<ApiSessionId> sessionId = sessionsApi.startAsInternalCustomer(
                new RemoteStartRequestV1()
                        .customerKeyId(customerKeyId.value)
                        .connectorId(connectorId.id));

        return sessionId.thenApply(id -> new ChargingSessionBuilder()
                .setConnectorId(connectorId)
                .setChargePointId(chargePointId)
                .setCustomerKeyId(customerKeyId)
                .setId(new ChargingSessionId(id.getSessionId()))
                .setStatus(WAITING_TO_START))
                .thenApply(this::populateSessionWithChargeSiteInfo)
                .thenApply(ChargingSessionBuilder::build)
                .join();
    }

    @Override
    public ChargingSessionEvent stopSession(TrustedUserId trustedUserId,
                                            ChargingSessionId sessionId,
                                            ChargePointId chargePointId) {
        return sessionsApi.stopAsInternalCustomer(new RemoteStopRequestV1()
                .userId(trustedUserId.value)
                .sessionId(sessionId.value))
                .thenApply(ok -> new ChargingSessionEvent(sessionId,
                        new ChargingSessionEventId(sessionId.value),
                        Instant.now(),
                        ChargingSessionEventNature.STOP_REQUESTED))
                .join();
    }

    @Override
    public ChargingSession fetchSession(ChargingSessionId sessionId) {
        return toChargingSession(sessionsRepository.getSession(sessionId.value));
    }

    @Override
    public List<ChargingSession> fetchOngoingSessions(TrustedUserId userId) {
        return sessionsRepository.getOngoingSessions(userId.value)
                .stream()
                .map(this::toChargingSession)
                .collect(toList());
    }

    private ChargingSession toChargingSession(final ApiSession session) {
        final Optional<ConnectorId> optConnectorId = Optional.ofNullable(session.getConnectorId()).map(ConnectorId::new);
        return new ChargingSession(new ChargingSessionId(session.getSessionId()),
                Optional.ofNullable(session.getChargingKeyId()).map(CustomerKeyId::new).orElse(null),
                Optional.ofNullable(session.getPrice()).map(this::toPrice).orElse(null),
                Optional.ofNullable(session.getStartTime()).orElse(null),
                Optional.ofNullable(session.getStopTime()).orElse(null),
                toSessionStatus(session.getSessionStatus()),
                optConnectorId.orElse(null),
                Optional.ofNullable(session.getChargePointId()).map(ChargePointId::new).orElse(null),
                optConnectorId.flatMap(this::getChargeSiteId).orElse(null)
        );
    }

    private Price toPrice(ApiAmount price) {
        return new Price(price.getAmountExcl(), price.getAmountIncl(), price.getCurrency());
    }

    private ChargingSessionStatus toSessionStatus(String sessionStatus) {
        switch (sessionStatus) {
            case "START_REQUESTED":
                return WAITING_TO_START;
            case "STARTED":
                return STARTED;
            case "STOP_REQUESTED":
                return WAITING_TO_STOP;
            case "STOPPED":
                return STOPPED;
            case "PRICED":
                return COMPLETE;
            default:
                throw new IllegalStateException(format("No explicit mapping of charging session state %s was found.", sessionStatus));
        }
    }

    private Optional<ChargeSiteId> getChargeSiteId(ConnectorId connectorId) {
        return assetPaymentsRepository.fetchChargePointInfo(connectorId)
                .map(info -> new ChargeSiteId(info.getBasicChargePoint().getChargePointSiteId()));
    }

    private ChargingSessionBuilder populateSessionWithChargeSiteInfo(ChargingSessionBuilder builder) {
        if (builder.getConnectorId() != null) {
            final Optional<ChargePointInfo> chargePointInfo =
                    assetPaymentsRepository.fetchChargePointInfo(builder.getConnectorId());
            chargePointInfo.ifPresent(info -> builder.setChargePointId(new ChargePointId(info.getId()))
                    .setChargeSiteId(new ChargeSiteId(info.getBasicChargePoint().getChargePointSiteId())));
        }
        return builder;
    }
}
