package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.ChargingKeyRepository;
import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.PagingCursor;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v2.request.ChargingKeyActivationRequest;
import com.tingcore.users.model.v2.request.ChargingKeyUpdateRequest;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ChargingKeyService {

    private final ChargingKeyRepository chargingKeyRepository;

    public ChargingKeyService(ChargingKeyRepository chargingKeyRepository) {
        this.chargingKeyRepository = chargingKeyRepository;
    }

    public ChargingKey activate(Long userId, String keyIdentifier) {
        ChargingKeyActivationRequest request = new ChargingKeyActivationRequest(keyIdentifier, userId);

        ApiResponse<ChargingKey> response = chargingKeyRepository.activate(userId, request);
        return response
                .getResponseOptional()
                .orElseThrow(() -> new UsersApiException(response.getError()));
    }

    public ChargingKey getChargingKeyById(final Long authorizedUserId, final Long chargingKeyId) {
        final ApiResponse<ChargingKey> apiResponse = chargingKeyRepository.getByChargingKeyId(authorizedUserId, chargingKeyId);
        return apiResponse.getResponseOptional().orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public PageResponse<ChargingKey> findChargingKeys(final Long authorizedUserId, final Integer limit, final Boolean fetchPrevious, final PagingCursor pagingCursor, final String keyIdentifierSearchQuery) {
        final ApiResponse<PageResponse<ChargingKey>> apiResponse = chargingKeyRepository.findByOwnerId(authorizedUserId, limit, fetchPrevious, pagingCursor, keyIdentifierSearchQuery);
        return apiResponse.getResponseOptional().orElseThrow(() -> new UsersApiException(apiResponse.getError()));
    }

    public ChargingKey updateChargingKey(final Long authorizedUserId, final Long chargingKeyId, final UpdateChargingKeyAppRequest chargingKeyAppRequest) {

        // Hämta den nuvarande nyckeln från user-service
        final ApiResponse<ChargingKey> chargingKeyApiResponse = chargingKeyRepository.getByChargingKeyId(authorizedUserId,chargingKeyId);
        final ChargingKey chargingKey = chargingKeyApiResponse.getResponseOptional().orElseThrow(() -> new UsersApiException(chargingKeyApiResponse.getError()));

        // Kolla så att owner id stämmer överens.
        if(authorizedUserId != chargingKey.getOwner().getId()){
            throw new EntityNotFoundException("Owner id does not match.");
        }

            // Mappa om det gamla objektet till det nya.
            final ChargingKeyUpdateRequest chargingKeyRequest = new ChargingKeyUpdateRequest(
                    chargingKey.getOwner().getId(), chargingKeyAppRequest.getChargingKeyName(), chargingKey.getKeyIdentifier(),
                    chargingKey.getEnabled(), chargingKey.getValidFrom(),chargingKey.getValidTo(), chargingKey.getType().getId(),
                    chargingKey.getAssignedPaymentOptions().stream().map(po -> po.getId()).collect(Collectors.toList()),chargingKey.getOwner().getVersion());
            final ApiResponse<ChargingKey> apiResponse = chargingKeyRepository.updateChargingKey(authorizedUserId, chargingKeyId, chargingKeyRequest);
            return apiResponse
                    .getResponseOptional()
                    .orElseThrow(() -> new UsersApiException(apiResponse.getError()));

    }
}
