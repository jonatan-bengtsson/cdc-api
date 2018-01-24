package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.AssetPaymentsRepository;
import com.tingcore.cdc.charging.repository.ChargingSessionRepository;
import com.tingcore.cdc.charging.repository.TokenRepository;
import com.tingcore.charging.assets.model.ChargePointInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ChargingSessionService {
    private final TokenRepository tokenRepository;
    private final ChargingSessionRepository chargingSessionRepository;
    private final AssetPaymentsRepository assetPaymentsRepository;

    public ChargingSessionService(final TokenRepository tokenRepository,
                                  final ChargingSessionRepository chargingSessionRepository,
                                  final AssetPaymentsRepository assetPaymentsRepository) {
        this.tokenRepository = notNull(tokenRepository);
        this.chargingSessionRepository = notNull(chargingSessionRepository);
        this.assetPaymentsRepository = notNull(assetPaymentsRepository);
    }

    public ChargingSession startSession(final TrustedUserId trustedUserId,
                                        final CustomerKeyId customerKeyId,
                                        final ChargePointId chargePointId,
                                        final ConnectorId connectorId) {
        final AuthorizationToken authorizationToken = tokenRepository.createToken(trustedUserId, customerKeyId, chargePointId);
        ChargingSession session = populateSessionWithChargeSiteInfo(chargingSessionRepository.createSession(trustedUserId, customerKeyId)
                .setConnectorId(connectorId))
                .build();
        chargingSessionRepository.startSession(session.id, authorizationToken, chargePointId, connectorId);
        return session;
    }

    public ChargingSessionEvent stopSession(final TrustedUserId trustedUserId,
                                            final ChargingSessionId sessionId,
                                            final ChargePointId chargePointId) {

        final ChargingSession chargingSession = populateSessionWithChargeSiteInfo(chargingSessionRepository.fetchSession(sessionId))
                .build();
        final AuthorizationToken authorizationToken = tokenRepository.createToken(trustedUserId, chargingSession.customerKeyId, chargePointId);

        return chargingSessionRepository.stopSession(sessionId, authorizationToken, chargePointId);
    }

    public ChargingSession fetchSession(final ChargingSessionId sessionId) {
        return populateSessionWithChargeSiteInfo(chargingSessionRepository.fetchSession(notNull(sessionId)))
                .build();
    }

    public List<ChargingSession> fetchOngoingSessions(TrustedUserId userId){
        return chargingSessionRepository.fetchSessionsForUserId(userId).stream()
                .map(builder -> populateSessionWithChargeSiteInfo(builder).build())
                .collect(toList());
    }

    private ChargingSessionBuilder populateSessionWithChargeSiteInfo(ChargingSessionBuilder builder) {
        if (builder.getConnectorId() != null) {
            final ChargePointInfo chargePointInfo = assetPaymentsRepository.fetchChargePointInfo(builder.getConnectorId());
            builder.setChargePointId(new ChargePointId(chargePointInfo.getId()))
                    .setChargeSiteId(new ChargeSiteId(chargePointInfo.getBasicChargePoint().getChargePointSiteId()));
        }
        return builder;
    }

}
