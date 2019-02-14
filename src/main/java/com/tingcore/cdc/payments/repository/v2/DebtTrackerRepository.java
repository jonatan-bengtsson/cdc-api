package com.tingcore.cdc.payments.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.exception.DebtTrackerApiException;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.payments.debttracker.api.v1.DebttrackerRestApi;
import com.tingcore.payments.debttracker.response.ApiDebtSummary;
import com.tingcore.payments.debttracker.response.ApiSessionDebt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tingcore.cdc.controller.ApiUtils.getResponseOrThrowError;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class DebtTrackerRepository extends AbstractApiRepository {

    private final DebttrackerRestApi debttrackerRestApi;
    private final Integer defaultTimeout;

    public DebtTrackerRepository(ObjectMapper objectMapper,
                                 final DebttrackerRestApi debttrackerRestApi,
                                 @Value("${app.debt-tracker.default-timeout:20}") final Integer defaultTimeout) {
        super(objectMapper);
        this.debttrackerRestApi = notNull(debttrackerRestApi);
        this.defaultTimeout = notNull(defaultTimeout);
    }

    public List<ApiSessionDebt> getAllDebtForUserAndCustomerKey(Long userId, Long customerKey) {
        final ApiResponse<List<ApiSessionDebt>> execute = execute(debttrackerRestApi.getDebtForIdentity(userId, customerKey));
        return getResponseOrThrowError(execute, DebtTrackerApiException::new);
    }


    public List<ApiDebtSummary> getDebtSummaryForUserAndCustomerKey(Long userId, Long customerKey) {
        final ApiResponse<List<ApiDebtSummary>> execute = execute(debttrackerRestApi.getDebtSummaryForIdentity(userId, customerKey));
        return getResponseOrThrowError(execute, DebtTrackerApiException::new);
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeout;
    }
}
