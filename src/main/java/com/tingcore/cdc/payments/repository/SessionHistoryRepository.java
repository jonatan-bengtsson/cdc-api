package com.tingcore.cdc.payments.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.external.ExternalApiException;
import com.tingcore.payments.cpo.api.SessionhistoryApi;
import com.tingcore.payments.cpo.model.ApiChargeHistory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class SessionHistoryRepository extends AbstractApiRepository {
    private final SessionhistoryApi sessionhistoryApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public SessionHistoryRepository(final ObjectMapper objectMapper,
                                    final SessionhistoryApi sessionhistoryApi) {
        super(notNull(objectMapper));
        this.sessionhistoryApi = sessionhistoryApi;
    }

    public List<ApiChargeHistory> getSessionHistory(final Long id,
                                                    final Long from,
                                                    final Long to) {
        return getResponseOrSessionHistoryError(sessionhistoryApi.getEmpSessionHistory(id, from, to));
    }

    private <T, E extends ExternalApiException> T getResponseOrSessionHistoryError(CompletableFuture<T> request) throws E {
        return getResponseOrThrowError(execute(request), GetSessionHistoryApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
