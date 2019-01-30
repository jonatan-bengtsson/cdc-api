package com.tingcore.cdc.sessionhistory.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.sessionhistory.repository.GetSessionHistoryApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.sessions.history.api.v1.ApiSessionHistoryContainer;
import com.tingcore.sessions.history.client.api.v1.SessionHistoryRestApi;
import org.springframework.stereotype.Repository;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class SessionHistoryRepository extends AbstractApiRepository {
    private final SessionHistoryRestApi restApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public SessionHistoryRepository(final ObjectMapper objectMapper,
                                    final SessionHistoryRestApi restApi) {
        super(notNull(objectMapper));
        this.restApi = restApi;
    }

    public ApiSessionHistoryContainer getSessionHistory(final Long chargingKeyId, final Long from, final Long to) {
        ApiResponse<ApiSessionHistoryContainer> fetchingHistory = execute(restApi.getChargingKeyHistoryTimeBoxed(chargingKeyId, from, to));
        return getResponseOrThrowError(fetchingHistory, GetSessionHistoryApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
