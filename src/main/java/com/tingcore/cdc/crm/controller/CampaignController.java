package com.tingcore.cdc.crm.controller;

import com.tingcore.campaign.model.product.Product;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.CampaignServiceException;
import com.tingcore.cdc.crm.service.CampaignService;
import com.tingcore.commons.rest.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/campaigns")
public class CampaignController {

    private final CampaignService service;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            path = "/{code}"
    )
    @ApiOperation(value = "Redeem campaign voucher",
            notes = "Route allows a user to redeem a campaign voucher",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEY_ORDERS)
    public List<Product> redeem(@PathVariable("code") String code) {
        if(authorizedUser.getOrganization() == null) {
            throw new CampaignServiceException(ErrorResponse.badRequest().message("Current user has no organization").build());
        }
        return service.redeem(authorizedUser.getId(), authorizedUser.getOrganization().getId(), code);
    }

}
