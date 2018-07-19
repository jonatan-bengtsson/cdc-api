package com.tingcore.cdc.crm.repository.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.crm.repository.AbstractUserServiceRepository;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.PagingCursor;
import com.tingcore.commons.rest.repository.ApiResponse;
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

    public ApiResponse<ChargingKey> getByChargingKeyId(final Long authorizedUserId, final Long chargingKeyId) {
        return execute(chargingKeysApi.getChargingKeyById(authorizedUserId, chargingKeyId));
    }

    public ApiResponse<PageResponse<ChargingKey>> findByOwnerId(final Long authorizedUserId, final Integer limit, final Boolean fetchPrevious, final PagingCursor pagingCursor, final String keyIdentifierSearchQuery) {
        return execute(chargingKeysApi.getChargingKeysByOwnerId(authorizedUserId, authorizedUserId, keyIdentifierSearchQuery,
                fetchPrevious, limit, pagingCursor.getSortField(), pagingCursor.getSortFieldCursor(), pagingCursor.getSortFieldSortOrder(),
                pagingCursor.getIdField(), pagingCursor.getIdFieldCursor(), pagingCursor.getIdFieldSortOrder()));
    }
}
