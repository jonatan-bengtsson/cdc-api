package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.model.BasicChargeSite;
import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.service.ChargePointSiteService;
import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

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

        // The organization id is the EMP id, but for now we have no choice
        // but to assume that the EMP is also the CPO to use.
        long chargePointOperatorId = this.authorizedUser.getOrganization().getId();
        return chargePointSiteService.getChargeSiteByCoordinate(latitude1, longitude1, latitude2, longitude2, chargePointOperatorId);
    }

    @RequestMapping(value = "/{chargePointSiteId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get complete Charge Point Site",
            notes = "Get complete Charge Point Site including Charge Points with Connectors",
            tags = SwaggerDocConstants.TAGS_CHARGE_POINT_SITES
    )
    public ChargePointSite getChargePointSite(@PathVariable("chargePointSiteId") String id) {
        long chargePointOperatorId = this.authorizedUser.getOrganization().getId();
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
