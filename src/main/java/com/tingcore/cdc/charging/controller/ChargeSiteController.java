package com.tingcore.cdc.charging.controller;

import com.tingcore.cdc.charging.model.ChargePointSite;
import com.tingcore.cdc.charging.model.BasicChargeSite;
import com.tingcore.cdc.charging.service.ChargeSiteService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.service.HashIdService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
// TODO Readd when spring security is configured correctly/removed
// @RequestMapping(value = "/v1")
@RequestMapping(value = "/chargesites")
public class ChargeSiteController {

    private final HashIdService hashIdService;
    private ChargeSiteService chargeSiteService;

    @Autowired
    public ChargeSiteController(ChargeSiteService chargeSiteService, HashIdService hashIdService) {
        this.chargeSiteService = chargeSiteService;
        this.hashIdService = hashIdService;
    }

    @RequestMapping(
            value = ";context=map",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get preview versions of Charge Point Sites",
            notes = "Get preview versions of Charge Point Sites bounded by the box representing the two coordinates. The bounds are not inclusive"
    )

    public PageResponse<BasicChargeSite> chargeSitesByCoordinates(
            @RequestParam("latitude1") double latitude1,
            @RequestParam("longitude1") double longitude1,
            @RequestParam("latitude2") double latitude2,
            @RequestParam("longitude2") double longitude2) throws ExecutionException, InterruptedException, IOException {

        return chargeSiteService.getChargeSiteByCoordinate(latitude1, longitude1, latitude2, longitude2);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get complete Charge Point Site",
            notes = "Get complete Charge Point Site including Charge Points with Connectors"
    )
    public ChargePointSite getChargePointSite(@PathVariable("id") String id) throws ExecutionException, InterruptedException, IOException {
        return chargeSiteService.getChargeSite(getId(id));
    }

    private long getId(String hashId) {
        Optional<Long> decode = hashIdService.decode(hashId);
        if(decode.isPresent()) {
            return decode.get();
        } else {
            if(1==1)
            return 1;
            throw new EntityNotFoundException();
        }
    }

}
