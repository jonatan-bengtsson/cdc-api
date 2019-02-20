package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.model.BasicChargeSite;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.service.ChargePointSiteService;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v1/charge-point-sites")
public class ChargePointSiteController {

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    private final HashIdService hashIdService;
    private final ChargePointSiteService chargePointSiteService;

    @Autowired
    public ChargePointSiteController(ChargePointSiteService chargePointSiteService, HashIdService hashIdService) {
        this.chargePointSiteService = chargePointSiteService;
        this.hashIdService = hashIdService;
    }

    @RequestMapping(value = "/view/map", method = RequestMethod.GET)
    @ApiOperation(value = "Get preview versions of Charge Point Sites",
            notes = "Get preview versions of Charge Point Sites bounded by the box representing the two coordinates. The bounds are not inclusive",
            tags = SwaggerDocConstants.TAGS_CHARGE_POINT_SITES
    )

    public PageResponse<BasicChargeSite> chargePointSitesByCoordinates(
            @RequestParam("northWestLatitude") double latitude1,
            @RequestParam("northWestLongitude") double longitude1,
            @RequestParam("southEastLatitude") double latitude2,
            @RequestParam("southEastLongitude") double longitude2) {

        long empId = this.authorizedUser.getOrganization().getId();
        return chargePointSiteService.getChargeSiteByCoordinate(latitude1, longitude1, latitude2, longitude2, empId);
    }

    @RequestMapping(value = "/{chargePointSiteId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get complete Charge Point Site",
            notes = "Get complete Charge Point Site including Charge Points with Connectors",
            tags = SwaggerDocConstants.TAGS_CHARGE_POINT_SITES
    )
    public ChargePointSite getChargePointSite(@PathVariable("chargePointSiteId") String id) {
        long empId = this.authorizedUser.getOrganization().getId();
        return chargePointSiteService.getChargeSite(getId(id), empId);
    }

    private long getId(String hashId) {
        return hashIdService.decode(hashId).orElseThrow(EntityNotFoundException::new);
    }

}
