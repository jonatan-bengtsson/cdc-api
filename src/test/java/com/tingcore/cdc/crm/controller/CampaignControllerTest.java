package com.tingcore.cdc.crm.controller;

import com.tingcore.campaign.model.product.PrepaidProduct;
import com.tingcore.campaign.model.product.Product;
import com.tingcore.campaign.model.product.ProductType;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.crm.service.CampaignService;
import com.tingcore.commons.api.model.Organization;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CampaignController.class)
public class CampaignControllerTest extends ControllerUnitTest {

    @MockBean CampaignService service;

    @Test
    public void redeem() throws Exception {
        given(authorizedUser.getOrganization()).willReturn(Organization.createBuilder().id(1L).build());
        given(service.redeem(anyLong(), anyLong(), anyString()))
                .willReturn(singletonList(new PrepaidProduct(100, "SEK")));

        final String code = "TEST2018";

        MockHttpServletRequestBuilder request = post("/v1/campaigns/redeem/"+code)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        Product[] products = mockMvcUtils.fromJson(response.getResponse().getContentAsString(), Product[].class);

        assertThat(products).hasSize(1);
        assertThat(products[0].getType()).isEqualTo(ProductType.PREPAID);
        PrepaidProduct result = (PrepaidProduct) products[0];
        assertThat(result.getAmount()).isEqualTo(100);
        assertThat(result.getCurrency()).isEqualTo("SEK");

    }

    @Test
    public void redeemFailNoOrg() throws Exception {
        given(authorizedUser.getOrganization()).willReturn(null);
        given(service.redeem(anyLong(), anyLong(), anyString()))
                .willReturn(singletonList(new PrepaidProduct(100, "SEK")));

        final String code = "TEST2018";

        MockHttpServletRequestBuilder request = post("/v1/campaigns/redeem/"+code)
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

}
