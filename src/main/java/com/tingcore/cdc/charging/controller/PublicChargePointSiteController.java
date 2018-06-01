package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.model.BasicChargeSite;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.service.ChargePointSiteService;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.service.v2.OrganizationService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/public/v1/charge-point-sites")
public class PublicChargePointSiteController {

    private final HashIdService hashIdService;
    private final ChargePointSiteService chargePointSiteService;
    private final OrganizationService organizationService;

    @Autowired
    public PublicChargePointSiteController(final ChargePointSiteService chargePointSiteService,
                                           final HashIdService hashIdService,
                                           final OrganizationService organizationService) {
        this.chargePointSiteService = chargePointSiteService;
        this.hashIdService = hashIdService;
        this.organizationService = organizationService;
    }

    @RequestMapping(
            value = "/view/map",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get preview versions of Charge Point Sites",
            notes = "Get preview versions of Charge Point Sites bounded by the box representing the two coordinates. The bounds are not inclusive. UserPrefix is ued to determine organization",
            tags = SwaggerDocConstants.TAGS_CHARGE_POINT_SITES
    )

    public PageResponse<BasicChargeSite> chargePointSitesByCoordinates(
            @RequestParam("northWestLatitude") double latitude1,
            @RequestParam("northWestLongitude") double longitude1,
            @RequestParam("southEastLatitude") double latitude2,
            @RequestParam("southEastLongitude") double longitude2,
            @RequestParam("userPrefix") String userPrefix) {

        final Long chargePointOperatorId = organizationService.getOrganizationByUserPrefix(userPrefix).getId();
        return chargePointSiteService
                .getChargeSiteByCoordinate(latitude1, longitude1, latitude2, longitude2, chargePointOperatorId);
    }

    @RequestMapping(
            value = "/{chargePointSiteId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get complete Charge Point Site",
            notes = "Get complete Charge Point Site including Charge Points with Connectors. UserPrefix is ued to determine organization",
            tags = SwaggerDocConstants.TAGS_CHARGE_POINT_SITES
    )
    public ChargePointSite getChargePointSite(@PathVariable("chargePointSiteId") String id,
                                              @RequestParam("userPrefix") String userPrefix) {
        final Long chargePointOperatorId = organizationService.getOrganizationByUserPrefix(userPrefix).getId();
        return chargePointSiteService.getChargeSite(getId(id), chargePointOperatorId);
    }

    private long getId(String hashId) {
        Optional<Long> decode = hashIdService.decode(hashId);
        if (decode.isPresent()) {
            return decode.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

}
