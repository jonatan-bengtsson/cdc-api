package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.model.PrivacyPolicyApproval;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.v2.PrivacyPoliciesApi;
import com.tingcore.users.model.v2.response.Agreement;
import org.springframework.stereotype.Repository;

/**
 * @author palmithor
 * @since 2018-05-22
 */
@Repository
public class PrivacyPolicyRepository extends AbstractUserServiceRepository {

    private final PrivacyPoliciesApi privacyPoliciesApi;


    public PrivacyPolicyRepository(final ObjectMapper objectMapper,
                                   final PrivacyPoliciesApi privacyPoliciesApi) {
        super(objectMapper);
        this.privacyPoliciesApi = privacyPoliciesApi;
    }

    public ApiResponse<PrivacyPolicyApproval> getCurrentApproval(final Long authorizedUserId, final Long organizationId) {
        final ApiResponse<Agreement> apiResponse = execute(privacyPoliciesApi.getActive(authorizedUserId, organizationId));
        return apiResponse.getResponseOptional()
                .map(agreement -> execute(privacyPoliciesApi.hasApproved(authorizedUserId, authorizedUserId, agreement.getId()))
                        .getResponseOptional()
                        .map(a -> new ApiResponse<>(new PrivacyPolicyApproval(agreement, Boolean.TRUE)))
                        .orElseGet(() -> {
                            if (apiResponse.getError().getStatusCode().equals(404)) {
                                return new ApiResponse<>(new PrivacyPolicyApproval(agreement, Boolean.TRUE));
                            }
                            return new ApiResponse<>(apiResponse.getError());
                        }))
                .orElseGet(() -> new ApiResponse<>(apiResponse.getError()));
    }

    public ApiResponse<Void> approve(final Long authorizedUserId, final Long privacyPolicyId) {
        return execute(privacyPoliciesApi.approve(authorizedUserId, authorizedUserId, privacyPolicyId));
    }
}
