package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.service.v2.ChargingKeyService;
import com.tingcore.users.model.v2.response.ChargingKey;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/v2/charging-keys")
public class ChargingKeyController {

    private final ChargingKeyService chargingKeyService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public ChargingKeyController(final ChargingKeyService chargingKeyService) {
        this.chargingKeyService = chargingKeyService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            path = "/activate/{keyIdentifier}"
    )
    @ApiOperation(value = "Activate charging key",
            notes = "Route allows to activate the charginf key identified by the given `keyIdentifier`",
            tags = SwaggerDocConstants.TAGS_CHARGING_KEYS)
    public ChargingKey activate(@PathVariable("keyIdentifier") String keyIdentifier) {
        return chargingKeyService.activate(authorizedUser.getId(), keyIdentifier);
    }
}
