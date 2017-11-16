package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.*;
import com.tingcore.cdc.charging.repository.ChargingSessionRepository;
import com.tingcore.cdc.charging.repository.TokenRepository;
import com.tingcore.cdc.model.UserReference;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Optional;

@Service
public class ChargingSessionService {
    private final TokenRepository tokenRepository;
    private final ChargingSessionRepository chargingSessionRepository;

    public ChargingSessionService(final TokenRepository tokenRepository,
                                  final ChargingSessionRepository chargingSessionRepository) {
        this.tokenRepository = notNull(tokenRepository);
        this.chargingSessionRepository = notNull(chargingSessionRepository);
    }

    public ChargingSession startSession(final UserReference userReference,
                                        final CustomerKeyId customerKeyId,
                                        final ChargePointId chargePointId,
                                        final ConnectorId connectorId) {
        final AuthorizationToken authorizationToken = tokenRepository.createToken(userReference, customerKeyId, chargePointId);
        ChargingSession session = chargingSessionRepository.createSession(customerKeyId);
        chargingSessionRepository.startSession(session.id, authorizationToken, chargePointId, connectorId);
        return session;
    }

    public Optional<ChargingSessionEvent> stopSession(final UserReference userReference,
                                                      final ChargingSessionId sessionId,
                                                      final ChargePointId chargePointId) {

      return chargingSessionRepository
          .fetchSession(sessionId)
          .map(chargingSession -> tokenRepository.createToken(userReference, chargingSession.customerKeyId, chargePointId))
          .map(authorizationToken -> chargingSessionRepository.stopSession(sessionId, authorizationToken));
    }

    public Optional<ChargingSession> fetchSession(final ChargingSessionId sessionId) {
      return chargingSessionRepository.fetchSession(notNull(sessionId));
    }
}
