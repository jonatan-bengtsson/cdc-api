package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private static final String PARAM_ID = "userId";
    private static final String PATH_PARAM_ID = "/{" + PARAM_ID + "}";

    private CustomerKeyService customerKeyService;
    private HashIdService hashIdService;

    public UserController(final CustomerKeyService customerKeyService, final HashIdService hashIdService) {
        this.customerKeyService = customerKeyService;
        this.hashIdService = hashIdService;
    }

    @ApiResponses({
            // In order for the Swagger configuration to pick up ErrorResponse.class.getSimpleName() and populate it with a
            // certain class it has to be defined at least for one route. This route was chosen.
            // https://github.com/springfox/springfox/issues/735
            @ApiResponse(code = SwaggerDefaultConstant.HTTP_STATUS_NOT_FOUND, message = SwaggerDefaultConstant.MESSAGE_NOT_FOUND, response = ErrorResponse.class)
    })
    @RequestMapping(
            value = PATH_PARAM_ID + "/keys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get customer keys by user id",
            notes = "Route allows fetching all customer keys that belong to a user.",
            tags = {SwaggerConstant.TAGS_CUSTOMER_KEYS, SwaggerConstant.TAGS_USERS})
    public PageResponse<CustomerKeyResponse> getCustomerKeys(@PathVariable(PARAM_ID) final String id) {
        return hashIdService.decode(id)
                .map(decodedId -> customerKeyService.findByUserId(decodedId))
                .orElseThrow(() -> new EntityNotFoundException(EntityNameConstant.USER, id));
    }
}
