package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.commons.rest.repository.AbstractApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AssetRepository extends AbstractApiRepository {

    private final ChargeSitesApi chargeSitesApi;

    @Override
    public Integer getTimeout() {
        return 120;
    }

    @Autowired
    public AssetRepository(final ObjectMapper objectMapper, final ChargeSitesApi chargeSitesApi) {
        super(objectMapper);
        this.chargeSitesApi = chargeSitesApi;
    }

    public ChargeSitesApi getChargeSitesApi() {
        return chargeSitesApi;
    }
}
