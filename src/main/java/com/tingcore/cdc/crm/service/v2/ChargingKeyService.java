package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.ChargingKeyRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v2.request.ChargingKeyActivationRequest;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.springframework.stereotype.Service;

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

}
