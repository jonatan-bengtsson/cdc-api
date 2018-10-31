package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.SessionsApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.sessions.api.v1.ApiSession;
import com.tingcore.sessions.client.api.v1.PaymentsSessionsRestApi;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tingcore.cdc.constant.SpringProfilesConstant.CHARGING_SESSIONS_V2;
import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
@Profile(CHARGING_SESSIONS_V2)
public class SessionsRepository extends AbstractApiRepository {

    private final PaymentsSessionsRestApi sessionsApi;
    private static final Integer defaultTimeout = 20;

    SessionsRepository(final ObjectMapper objectMapper,
                       final PaymentsSessionsRestApi sessionsApi) {
        super(notNull(objectMapper));
        this.sessionsApi = notNull(sessionsApi);
    }

    public ApiSession getSession(final Long sessionId) {
        ApiResponse<ApiSession> session = execute(sessionsApi.getSession(sessionId));
        return getResponseOrThrowError(session, SessionsApiException::new);
    }

    public List<ApiSession> getOngoingSessions(final Long userId) {
        ApiResponse<List<ApiSession>> sessions = execute(sessionsApi.getOngoingSessions(userId));
        return getResponseOrThrowError(sessions, SessionsApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }
}
