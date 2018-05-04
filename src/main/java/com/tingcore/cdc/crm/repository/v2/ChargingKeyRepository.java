package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.users.api.v2.ChargingKeysApi;
import com.tingcore.users.model.v2.request.ChargingKeyActivationRequest;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.springframework.stereotype.Repository;

@Repository
public class ChargingKeyRepository extends AbstractUserServiceRepository {

    private final ChargingKeysApi chargingKeysApi;

    ChargingKeyRepository(ObjectMapper objectMapper, ChargingKeysApi chargingKeysApi) {
        super(objectMapper);
        this.chargingKeysApi = chargingKeysApi;
    }

    public ApiResponse<ChargingKey> activate(Long userId, ChargingKeyActivationRequest request) {
        return execute(chargingKeysApi.activateChargingKey(userId, request));
    }

}
