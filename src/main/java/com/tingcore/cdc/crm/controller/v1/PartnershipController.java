package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.service.v1.PartnershipService;
import com.tingcore.partnerships.model.v1.request.CustomerAssociationCreateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1/partnerships/{partnershipId}")
public class PartnershipController {
    private final PartnershipService partnershipService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public PartnershipController(final PartnershipService partnershipService) {
        this.partnershipService = partnershipService;
    }

    @PostMapping(path = "/customers/{externalCustomerId}")
    @ApiOperation(value = "Create a new Customer association",
            notes = "Route allows creating a new Customer association between an internal user id and an external user id",
            tags = SwaggerDocConstants.TAGS_PARTNERSHIPS)
    public void createCustomerAssociation(
            @ApiParam(value = "partnershipId", required = true)
            @PathVariable("partnershipId") final String partnershipId,
            @ApiParam(value = "externalCustomerId", required = true)
            @PathVariable("externalCustomerId") final String externalCustomerId)
    {
        final Long organizationId = authorizedUser.getOrganization().getId();
        final CustomerAssociationCreateRequest request = new CustomerAssociationCreateRequest(
                authorizedUser.getId(),
                externalCustomerId);
        partnershipService.createCustomerAssociation(organizationId, partnershipId, request);
    }
}
