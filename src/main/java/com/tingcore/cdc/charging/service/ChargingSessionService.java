package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.ChargingSession;
import com.tingcore.cdc.charging.model.ChargingSessionEvent;
import com.tingcore.cdc.charging.model.ChargingSessionId;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.model.TrustedUserId;
import java.util.List;

public interface ChargingSessionService {

    ChargingSession startSession(TrustedUserId trustedUserId,
                                 CustomerKeyId customerKeyId,
                                 ChargePointId chargePointId,
                                 ConnectorId connectorId);

    ChargingSessionEvent stopSession(TrustedUserId trustedUserId,
                                     ChargingSessionId sessionId,
                                     ChargePointId chargePointId);

    ChargingSession fetchSession(ChargingSessionId sessionId);

    List<ChargingSession> fetchOngoingSessions(TrustedUserId userId);
}
