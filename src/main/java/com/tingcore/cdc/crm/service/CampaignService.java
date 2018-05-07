package com.tingcore.cdc.crm.service;

import com.tingcore.campaign.model.product.Product;
import com.tingcore.campaign.model.request.RedeemCodeRequest;
import com.tingcore.cdc.crm.model.CampaignServiceException;
import com.tingcore.cdc.crm.repository.CampaignRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository repository;

    public CampaignService(CampaignRepository repository) {
        this.repository = repository;
    }

    public List<Product> redeem(Long userId, Long organizationId, String campaignCode) {
        RedeemCodeRequest request = new RedeemCodeRequest(userId, organizationId, campaignCode);
        ApiResponse<List<Product>> response = repository.redeem(request);
        return response
                .getResponseOptional()
                .orElseThrow(() -> new CampaignServiceException(response.getError()));
    }

}
