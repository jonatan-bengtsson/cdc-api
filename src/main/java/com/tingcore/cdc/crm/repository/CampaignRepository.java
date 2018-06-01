package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.campaign.client.CampaignServiceApi;
import com.tingcore.campaign.model.product.Product;
import com.tingcore.campaign.model.request.RedeemCodeRequest;
import com.tingcore.commons.rest.repository.ApiResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CampaignRepository extends AbstractUserServiceRepository {

    private final CampaignServiceApi campaignServiceApi;

    public CampaignRepository(final ObjectMapper objectMapper, final CampaignServiceApi campaignServiceApi) {
        super(objectMapper);
        this.campaignServiceApi = campaignServiceApi;
    }

    public ApiResponse<List<Product>> redeem(RedeemCodeRequest request) {
        return execute(campaignServiceApi.redeem(request));
    }

}
