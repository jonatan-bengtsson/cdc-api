package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.model.MapPreviewChargeSite;
import com.tingcore.cdc.charging.service.ChargeSiteService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class ChargeSiteController {

    private ChargeSiteService chargeSiteService;

    @Autowired
    public ChargeSiteController(ChargeSiteService chargeSiteService) {
        this.chargeSiteService = chargeSiteService;
    }

    @RequestMapping(
            value = "/chargesites;context=map",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get preview versions of Charge Point Sites",
            notes = "Route allows fetching all customer keys that belong to a user."
    )

    public PageResponse<MapPreviewChargeSite> chargeSitesByCoordinates(
            @RequestParam("latitude1") double latitude1,
            @RequestParam("longitude1") double longitude1,
            @RequestParam("latitude2") double latitude2,
            @RequestParam("longitude2") double longitude2) {

        return chargeSiteService.getChargeSiteByCoordinate(latitude1, longitude1, latitude2, longitude2);

    }
}
