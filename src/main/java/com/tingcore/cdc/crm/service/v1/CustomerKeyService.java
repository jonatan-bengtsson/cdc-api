package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.repository.v1.CustomerKeyRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.payments.repository.v2.DebtCollectRepository;
import com.tingcore.cdc.payments.repository.v2.DebtTrackerRepository;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v1.request.UserPaymentOptionIdRequest;
import com.tingcore.users.model.v1.response.CustomerKeyResponse;
import com.tingcore.users.model.v1.response.CustomerKeyTypeResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@Service
public class CustomerKeyService {

    private final CustomerKeyRepository customerKeyRepository;
    private final DebtTrackerRepository debtTrackerRepository;
    private final DebtCollectRepository debtCollectRepository;

    CustomerKeyService(final CustomerKeyRepository customerKeyRepository,
                       final DebtTrackerRepository debtTrackerRepository,
                       final DebtCollectRepository debtCollectRepository) {
        this.customerKeyRepository = customerKeyRepository;
        this.debtTrackerRepository = debtTrackerRepository;
        this.debtCollectRepository = debtCollectRepository;
    }

    public PageResponse<CustomerKey> findByUserId(final Long authorizedUserId) {
        final ApiResponse<PageResponse<CustomerKeyResponse>> apiResponse = customerKeyRepository.findByUserId(authorizedUserId);
        return apiResponse
                .getResponseOptional()
                .map(apiPageResponse -> new PageResponse<>(apiPageResponse.getContent()
                                                                   .stream()
                                                                   .map(CustomerKeyMapper::toModel)
                                                                   .collect(Collectors.toList()), apiPageResponse.getPagination()))
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public CustomerKey findByCustomerKeyId(final Long authorizedUserId, final Long customerKeyId) {
        final ApiResponse<CustomerKeyResponse> apiResponse = customerKeyRepository.findById(authorizedUserId, customerKeyId);
        return apiResponse
                .getResponseOptional()
                .map(CustomerKeyMapper::toModel)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public List<CustomerKeyType> getCustomerKeyTypes() {
        final ApiResponse<List<CustomerKeyTypeResponse>> apiResponse = customerKeyRepository.findCustomerKeyTypes();
        return apiResponse.getResponseOptional()
                .map(customerKeyTypeResponses -> customerKeyTypeResponses.stream().map(CustomerKeyTypeMapper::toModel).collect(Collectors.toList()))
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public CustomerKey addUserPaymentOption(Long authorizedUserId, Long customerKeyId, Long userPaymentOptionId) {
        UserPaymentOptionIdRequest apiRequest = new UserPaymentOptionIdRequest(userPaymentOptionId);
        final ApiResponse<CustomerKeyResponse> apiResponse = customerKeyRepository.addUserPaymentOption(customerKeyId, apiRequest, authorizedUserId);

        // temporary solution while we decide what to do in user service regarding kafka and sns/sqs
        // Don't want to destroy the response for updating paymentoption if debt collect fails
        if (!apiResponse.hasError()) {
            debtTrackerRepository.getSessionsInForUserAndChargingKeyId(authorizedUserId, customerKeyId)
                    .getResponseOptional()
                    .map(debtCollectRepository::clearSessions);
        }

        return apiResponse
                .getResponseOptional()
                .map(CustomerKeyMapper::toModel)
                .orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public void deleteUserPaymentOption(Long authorizedUserId, Long customerKeyId, Long userPaymentOptionId) {
        final ApiResponse<Void> apiResponse = customerKeyRepository.deleteUserPaymentOption(customerKeyId, userPaymentOptionId, authorizedUserId);
        apiResponse
                .getErrorOptional()
                .ifPresent(error -> {
                    throw new UsersApiException(error);
                });
    }
}
