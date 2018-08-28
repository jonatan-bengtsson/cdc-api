package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.cdc.crm.service.v2.ChargingKeyService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.ExternalPagingCursor;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.service.PagingConverterService;
import com.tingcore.users.model.v2.response.ChargingKey;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/v2/charging-keys")
public class ChargingKeyController {

    private final ChargingKeyService chargingKeyService;
    private final HashIdService hashIdService;
    private final PagingConverterService pagingConverterService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public ChargingKeyController(final ChargingKeyService chargingKeyService,
                                 final HashIdService hashIdService,
                                 final PagingConverterService pagingConverterService) {
        this.chargingKeyService = chargingKeyService;
        this.hashIdService = hashIdService;
        this.pagingConverterService = pagingConverterService;
    }

    @RequestMapping(method = GET)
    @ApiOperation(value = "Get charging keys by owner id",
            notes = "Route allows fetching the charging keys associated with an owner(user or organization) id. " +
                    "The route is paginated.",
            tags = SwaggerDocConstants.TAGS_CHARGING_KEYS)
    public PageResponse<ChargingKey> getChargingKeys(
            @RequestParam(value = "fetchPrevious", required = false, defaultValue = "false") final Boolean fetchPrevious,
            @RequestParam(value = "limit", required = false, defaultValue = "30") final Integer limit,
            ExternalPagingCursor pagingCursor,
            @RequestParam(value = QueryParameterConstant.KEY_IDENTIFIER_QUERY, required = false) @Size(max = 20) final String keyIdentifierSearchQuery) {
        return chargingKeyService.findChargingKeys(authorizedUser.getId(), limit, fetchPrevious, pagingConverterService.convertToInternal(pagingCursor), keyIdentifierSearchQuery);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/activate/{keyIdentifier}")
    @ApiOperation(value = "Activate charging key",
            notes = "Route allows to activate the charginf key identified by the given `keyIdentifier`",
            tags = SwaggerDocConstants.TAGS_CHARGING_KEYS)
    public ChargingKey activate(@PathVariable("keyIdentifier") String keyIdentifier) {
        return chargingKeyService.activate(authorizedUser.getId(), keyIdentifier);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{chargingKeyId}")
    @ApiOperation(
            value = "Get by id",
            notes = "Route allows fetching a charging key by charging key id.",
            tags = SwaggerDocConstants.TAGS_CHARGING_KEYS
    )
    public ChargingKey getChargingKeyById(
            @ApiParam(value = "The charging key id", example = SwaggerDocConstants.EXAMPLE_ID, required = true)
            @PathVariable("chargingKeyId") final String chargingKeyId
    ) {
        return hashIdService.decode(chargingKeyId)
                .map(encodedId -> chargingKeyService.getChargingKeyById(authorizedUser.getId(), encodedId))
                .orElseThrow(() -> new EntityNotFoundException(ChargingKey.class.getSimpleName(), chargingKeyId));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{chargingKeyId}")
    @ApiOperation(
            value = "Update a charging key",
            notes = "Route allows updating a charging key by id.",
            tags = SwaggerDocConstants.TAGS_CHARGING_KEYS
    )
    public ChargingKey updateChargingKey(
            @PathVariable(value = "chargingKeyId") String encodedChargingKeyId,
            @Valid @RequestBody UpdateChargingKeyAppRequest updateChargingKeyRequest){
        final Long chargingKeyId = hashIdService.decode(encodedChargingKeyId).orElseThrow(() -> new EntityNotFoundException(ChargingKey.class.getSimpleName(), encodedChargingKeyId));
        return chargingKeyService.updateChargingKey(authorizedUser.getId(), chargingKeyId, updateChargingKeyRequest);

    }
}
