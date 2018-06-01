package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.model.PrivacyPolicyApproval;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.cdc.crm.repository.v2.PrivacyPolicyRepository;
import com.tingcore.cdc.crm.repository.v2.TermsAndConditionRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.rest.repository.ApiResponse;
import org.springframework.stereotype.Service;

/**
 * @author palmithor
 * @since 2018-05-22
 */
@Service
public class AgreementService {

    private final PrivacyPolicyRepository privacyPolicyRepository;
    private final TermsAndConditionRepository termsAndConditionRepository;

    public AgreementService(final PrivacyPolicyRepository privacyPolicyRepository, final TermsAndConditionRepository termsAndConditionRepository) {
        this.privacyPolicyRepository = privacyPolicyRepository;
        this.termsAndConditionRepository = termsAndConditionRepository;
    }

    public PrivacyPolicyApproval getCurrentPrivacyPolicyApproval(final Long authorizedUserId, final Long organizationId) {
        final ApiResponse<PrivacyPolicyApproval> apiResponse = privacyPolicyRepository.getCurrentApproval(authorizedUserId, organizationId);
        return apiResponse.getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public void approvePrivacyPolicy(final Long authorizedUserId, final Long agreementId) {
        final ApiResponse<Void> apiResponse = privacyPolicyRepository.approve(authorizedUserId, agreementId);
        apiResponse.getErrorOptional()
                .ifPresent(errorResponse -> {
                    throw new UsersApiException(errorResponse);
                });
    }

    public TermsAndConditionsApproval getCurrentTermsAndConditionsApproval(final Long authorizedUserId, final Long organizationId) {
        final ApiResponse<TermsAndConditionsApproval> apiResponse = termsAndConditionRepository.getCurrentApproval(authorizedUserId, organizationId);
        return apiResponse.getResponseOptional()
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public void approveTermsAndConditions(final Long authorizedUserId, final Long agreementId) {
        final ApiResponse<Void> apiResponse = termsAndConditionRepository.approve(authorizedUserId, agreementId);
        apiResponse.getErrorOptional()
                .ifPresent(errorResponse -> {
                    throw new UsersApiException(errorResponse);
                });
    }
}
