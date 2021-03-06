package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.api.v2.TermsAndConditionsApi;
import com.tingcore.users.model.v2.response.Agreement;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2018-05-22
 */
@Repository
public class TermsAndConditionRepository extends AbstractUserServiceRepository {


    private final TermsAndConditionsApi termsAndConditionsApi;


    public TermsAndConditionRepository(final ObjectMapper objectMapper,
                                       final TermsAndConditionsApi termsAndConditionsApi) {
        super(objectMapper);
        this.termsAndConditionsApi = termsAndConditionsApi;
    }

    public ApiResponse<TermsAndConditionsApproval> getCurrentApproval(final Long authorizedUserId, final Long organizationId) {
        final ApiResponse<Agreement> apiResponse = execute(termsAndConditionsApi.getCustomersActive(authorizedUserId, organizationId));
        return apiResponse.getResponseOptional()
                .map(agreement -> {
                    final ApiResponse<Agreement> approvalApiResponse = execute(termsAndConditionsApi.hasApproved(authorizedUserId, authorizedUserId, agreement.getId()));
                    return approvalApiResponse
                            .getResponseOptional()
                            .map(a -> new ApiResponse<>(new TermsAndConditionsApproval(agreement, Boolean.TRUE)))
                            .orElseGet(() -> {
                                if (approvalApiResponse.getError().getStatusCode().equals(404)) {
                                    return new ApiResponse<>(new TermsAndConditionsApproval(agreement, Boolean.FALSE));
                                }
                                return new ApiResponse<>(apiResponse.getError());
                            });
                })
                .orElseGet(() -> new ApiResponse<>(apiResponse.getError()));
    }

    public ApiResponse<Void> approve(final Long authorizedUserId, final Long agreementId) {
        return execute(termsAndConditionsApi.approve(authorizedUserId, authorizedUserId, agreementId));
    }
}
