package com.tingcore.cdc.charging.service.v2;

import static com.tingcore.cdc.constant.SpringProfilesConstant.CHARGING_SESSIONS_V2;
import static org.apache.commons.lang3.Validate.notNull;

import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.ChargeSiteId;
import com.tingcore.cdc.charging.model.ChargingSession;
import com.tingcore.cdc.charging.model.ChargingSessionBuilder;
import com.tingcore.cdc.charging.model.ChargingSessionEvent;
import com.tingcore.cdc.charging.model.ChargingSessionEventId;
import com.tingcore.cdc.charging.model.ChargingSessionEventNature;
import com.tingcore.cdc.charging.model.ChargingSessionId;
import com.tingcore.cdc.charging.model.ChargingSessionStatus;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.model.TrustedUserId;
import com.tingcore.cdc.charging.repository.AssetPaymentsRepository;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.charging.assets.model.ChargePointInfo;
import com.tingcore.payments.cpo.api.SessionsApi;
import com.tingcore.payments.cpo.model.ApiSessionId;
import com.tingcore.payments.cpo.model.RemoteStartRequestV1;
import com.tingcore.payments.cpo.model.RemoteStopRequestV1;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(CHARGING_SESSIONS_V2)
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private final SessionsApi sessionsApi;
    private final AssetPaymentsRepository assetPaymentsRepository;

    public ChargingSessionServiceImpl(SessionsApi sessionsApi,
                                      AssetPaymentsRepository assetPaymentsRepository) {
        this.sessionsApi = notNull(sessionsApi);
        this.assetPaymentsRepository = notNull(assetPaymentsRepository);
    }

    @Override
    public ChargingSession startSession(TrustedUserId trustedUserId, CustomerKeyId customerKeyId,
                                        ChargePointId chargePointId, ConnectorId connectorId) {
        CompletableFuture<ApiSessionId> sessionId = sessionsApi.startAsInternalCustomer(
            new RemoteStartRequestV1()
                .customerKeyId(customerKeyId.value)
                .connectorId(connectorId.id));

        return sessionId.thenApply(id -> new ChargingSessionBuilder().setConnectorId(connectorId)
                                                                     .setChargePointId(chargePointId)
                                                                     .setCustomerKeyId(customerKeyId)
                                                                     .setId(new ChargingSessionId(id.getSessionId()))
                                                                     .setStatus(ChargingSessionStatus.WAITING_TO_START))
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
        throw new NotImplementedException("Fetch Session: normal service will be resumed as soon as possible");
    }

    @Override
    public List<ChargingSession> fetchOngoingSessions(TrustedUserId userId) {
        throw new NotImplementedException("Fetch Ongoing Sessions: coming soon, to a location near you");
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
