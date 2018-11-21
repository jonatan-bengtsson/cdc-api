package com.tingcore.cdc.charging.service.v1;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.AssetPaymentsRepository;
import com.tingcore.cdc.charging.repository.ChargingSessionRepository;
import com.tingcore.cdc.charging.repository.TokenRepository;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.charging.assets.model.ChargePointInfo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tingcore.cdc.constant.SpringProfilesConstant.CHARGING_SESSIONS_V2;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@Service
@Profile("!" + CHARGING_SESSIONS_V2)
public class ChargingSessionServiceImpl implements ChargingSessionService {
    private final TokenRepository tokenRepository;
    private final ChargingSessionRepository chargingSessionRepository;
    private final AssetPaymentsRepository assetPaymentsRepository;

    public ChargingSessionServiceImpl(final TokenRepository tokenRepository,
                                  final ChargingSessionRepository chargingSessionRepository,
                                  final AssetPaymentsRepository assetPaymentsRepository) {
        this.tokenRepository = notNull(tokenRepository);
        this.chargingSessionRepository = notNull(chargingSessionRepository);
        this.assetPaymentsRepository = notNull(assetPaymentsRepository);
    }

    @Override public ChargingSession startSession(final TrustedUserId trustedUserId,
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

    @Override public ChargingSessionEvent stopSession(final TrustedUserId trustedUserId,
                                            final ChargingSessionId sessionId,
                                            final ChargePointId chargePointId) {

        final ChargingSession chargingSession = populateSessionWithChargeSiteInfo(chargingSessionRepository.fetchSession(sessionId))
                .build();
        final AuthorizationToken authorizationToken = tokenRepository.createToken(trustedUserId, chargingSession.customerKeyId, chargePointId);

        return chargingSessionRepository.stopSession(sessionId, authorizationToken, chargePointId);
    }

    @Override public ChargingSession fetchSession(final ChargingSessionId sessionId) {
        return populateSessionWithChargeSiteInfo(chargingSessionRepository.fetchSession(notNull(sessionId)))
                .build();
    }

    @Override public List<ChargingSession> fetchOngoingSessions(TrustedUserId userId){
        return chargingSessionRepository.fetchSessionsForUserId(userId).stream()
                .map(builder -> populateSessionWithChargeSiteInfo(builder).build())
                .collect(toList());
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
