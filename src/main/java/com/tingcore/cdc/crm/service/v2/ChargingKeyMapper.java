package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.users.model.v2.response.BaseResponse;
import com.tingcore.users.model.v2.response.ChargingKey;
import com.tingcore.users.model.v3.request.ChargingKeyUpdateRequest;

import java.util.Optional;

public class ChargingKeyMapper {

    private ChargingKeyMapper() {
    }

    static ChargingKeyUpdateRequest toApiRequest(final UpdateChargingKeyAppRequest appRequest, final ChargingKey chargingKey){
        return ChargingKeyUpdateRequest.createBuilder()
                .enabled(chargingKey.getEnabled())
                .keyIdentifier(chargingKey.getKeyIdentifier())
                .name(appRequest.getChargingKeyName())
                .ownerId(chargingKey.getOwner().getId())
                .typeId(Optional.ofNullable(chargingKey.getType()).map(BaseResponse::getId).orElse(null))
                .validFrom(chargingKey.getValidFrom())
                .validTo(chargingKey.getValidTo())
                .version(chargingKey.getVersion())
                .build();
    }
}
