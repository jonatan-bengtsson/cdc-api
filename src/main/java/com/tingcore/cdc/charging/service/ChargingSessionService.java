package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.ChargingSessionRepository;
import com.tingcore.cdc.charging.repository.TokenRepository;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class ChargingSessionService {
    private final TokenRepository tokenRepository;
    private final ChargingSessionRepository chargingSessionRepository;

    public ChargingSessionService(final TokenRepository tokenRepository,
                                  final ChargingSessionRepository chargingSessionRepository) {
        this.tokenRepository = notNull(tokenRepository);
        this.chargingSessionRepository = notNull(chargingSessionRepository);
    }

    public ChargingSession startSession(final TrustedUserId trustedUserId,
                                        final CustomerKeyId customerKeyId,
                                        final ChargePointId chargePointId,
                                        final ConnectorId connectorId) {
        final AuthorizationToken authorizationToken = tokenRepository.createToken(trustedUserId, customerKeyId, chargePointId);
        ChargingSession session = chargingSessionRepository.createSession(trustedUserId, customerKeyId);
        chargingSessionRepository.startSession(session.id, authorizationToken, chargePointId, connectorId);
        return session;
    }

    public ChargingSessionEvent stopSession(final TrustedUserId trustedUserId,
                                            final ChargingSessionId sessionId,
                                            final ChargePointId chargePointId) {

        final ChargingSession chargingSession = chargingSessionRepository.fetchSession(sessionId);
        final AuthorizationToken authorizationToken = tokenRepository.createToken(trustedUserId, chargingSession.customerKeyId, chargePointId);

        return chargingSessionRepository.stopSession(sessionId, authorizationToken, chargePointId);
    }

    public ChargingSession fetchSession(final ChargingSessionId sessionId) {
        return chargingSessionRepository.fetchSession(notNull(sessionId));
    }
}
