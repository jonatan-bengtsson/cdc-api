package com.tingcore.cdc.charging.service;

import com.tingcore.cdc.charging.repository.AssetRepository;
import com.tingcore.cdc.charging.repository.OperationsRepository;
import com.tingcore.charging.assets.api.ChargeSitesApi;
import com.tingcore.charging.assets.model.ChargePointSiteEntity;
import com.tingcore.charging.assets.model.CompleteChargePointSite;
import com.tingcore.commons.api.service.ForbiddenException;
import com.tingcore.commons.rest.repository.ApiResponse;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChargePointSiteServiceTest {

    @Test(expected = ForbiddenException.class)
    public void getChargeSite() throws Exception {
        CompletableFuture<CompleteChargePointSite> cpsFuture = mockCompletableFuture();
        ChargeSitesApi chargeSitesApi = mockChargeSitesApi(cpsFuture);

        final ChargePointSiteService cpsService = new ChargePointSiteService(mockAssetRepository(chargeSitesApi),
                mockOperationsRepository(), null);

        final long chargePointSiteId = 1L;
        final long chargePointOperatorId = 1L; // But site 1 has 4711 as CPO
        cpsService.getChargeSite(chargePointSiteId, chargePointOperatorId);

    }

    @SuppressWarnings("unchecked")
    private AssetRepository mockAssetRepository(ChargeSitesApi chargeSitesApi) {
        AssetRepository assetRepository = mock(AssetRepository.class);
        when(assetRepository.getChargeSitesApi()).thenReturn(chargeSitesApi);
        when(assetRepository.execute(anyObject())).thenAnswer(invocationOnMock -> {
                    CompletableFuture<CompleteChargePointSite> future = (CompletableFuture<CompleteChargePointSite>) (invocationOnMock.getArguments()[0]);
                    return new ApiResponse<>(future.get(0L, TimeUnit.SECONDS));
                }
        );
        return assetRepository;
    }

    private OperationsRepository mockOperationsRepository() {
        OperationsRepository operationsRepository = mock(OperationsRepository.class);
        when(operationsRepository.getOperationsApi()).thenReturn(null);
        return operationsRepository;
    }


    private ChargeSitesApi mockChargeSitesApi(CompletableFuture<CompleteChargePointSite> completeChargePointSiteCompletableFuture) {
        ChargeSitesApi chargeSitesApi = mock(ChargeSitesApi.class);
        when(chargeSitesApi.findCompleteChargeSiteByIdUsingGET(anyLong())).thenReturn(completeChargePointSiteCompletableFuture);
        return chargeSitesApi;
    }

    @SuppressWarnings("unchecked")
    private CompletableFuture<CompleteChargePointSite> mockCompletableFuture() throws Exception {
        com.tingcore.charging.assets.model.ChargePointSite site = new com.tingcore.charging.assets.model.ChargePointSite();
        site.setOperatorOrganizationId(4711L);
        ChargePointSiteEntity siteEntity = new ChargePointSiteEntity();
        siteEntity.setData(site);

        CompleteChargePointSite completeChargePointSite = new CompleteChargePointSite();
        completeChargePointSite.setChargePointSiteEntity(siteEntity);

        CompletableFuture<CompleteChargePointSite> f = mock(CompletableFuture.class);
        when(f.get(anyLong(), eq(TimeUnit.SECONDS))).thenReturn(completeChargePointSite);
        return f;
    }
}
