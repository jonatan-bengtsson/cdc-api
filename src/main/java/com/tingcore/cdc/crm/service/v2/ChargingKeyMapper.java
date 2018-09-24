package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.users.model.v2.request.ChargingKeyUpdateRequest;
import com.tingcore.users.model.v2.response.BaseResponse;
import com.tingcore.users.model.v2.response.ChargingKey;

import java.util.Optional;
import java.util.stream.Collectors;

public class ChargingKeyMapper {

    public ChargingKeyMapper() {
    }

    static ChargingKeyUpdateRequest toApiRequest(final UpdateChargingKeyAppRequest appRequest, final ChargingKey chargingKey){

        return new ChargingKeyUpdateRequest(
                chargingKey.getOwner().getId(),
                appRequest.getChargingKeyName(),
                Optional.ofNullable(chargingKey.getKeyIdentifier()).orElse(null),
                Optional.ofNullable(chargingKey.getEnabled()).orElse(null),
                Optional.ofNullable(chargingKey.getValidFrom()).orElse(null),
                Optional.ofNullable(chargingKey.getValidTo()).orElse(null),
                Optional.ofNullable(chargingKey.getType()).map(BaseResponse::getId).orElse(null),
                Optional.ofNullable(chargingKey.getAssignedPaymentOptions())
                        .map(assignedPaymentOptions -> assignedPaymentOptions
                                .stream()
                                .map(paymentOption -> paymentOption.getId())
                                .collect(Collectors.toList()))
                        .orElse(null),
                chargingKey.getVersion(),
        null);
    }
}
