package com.tingcore.cdc.crm.service;

import com.tingcore.campaign.model.product.PrepaidProduct;
import com.tingcore.campaign.model.product.Product;
import com.tingcore.campaign.model.product.ProductType;
import com.tingcore.campaign.model.request.RedeemCodeRequest;
import com.tingcore.cdc.crm.exception.CampaignServiceException;
import com.tingcore.cdc.crm.repository.CampaignRepository;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CampaignServiceTest {

    @MockBean private CampaignRepository repository;

    private CampaignService unitUnderTest;

    private final Long USER_ID = 1L;
    private final Long ORG_ID = 1L;
    private final String CODE = "TEST2018";

    @Before
    public void setUp() {
        unitUnderTest = new CampaignService(repository);
    }

    @Test
    public void redeem() {
        when(repository.redeem(any(RedeemCodeRequest.class)))
                .thenReturn(new ApiResponse<>(singletonList(new PrepaidProduct(100, "SEK"))));

        List<Product> result = unitUnderTest.redeem(USER_ID, ORG_ID, CODE);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(ProductType.PREPAID);
    }

    @Test
    public void redeemFail() {
        when(repository.redeem(any(RedeemCodeRequest.class)))
                .thenReturn(new ApiResponse<>(ErrorResponse.badRequest().build()));

        assertThatExceptionOfType(CampaignServiceException.class)
                .isThrownBy(() -> unitUnderTest.redeem(USER_ID, ORG_ID, CODE));
    }


}
