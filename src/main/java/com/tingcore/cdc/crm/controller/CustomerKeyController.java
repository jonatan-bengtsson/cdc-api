package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.request.CustomerKeyPostRequest;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@RestController
@RequestMapping(value = "/v1/customer-keys")
public class CustomerKeyController {

    private final CustomerKeyService customerKeyService;
    private final HashIdService hashIdService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CustomerKeyController(final CustomerKeyService customerKeyService,
                                 final HashIdService hashIdService) {
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
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get customer keys",
            notes = "Route allows fetching the authorized user's customer keys",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS)
    public PageResponse<CustomerKey> getCustomerKeys() {
        return customerKeyService.findByUserId(authorizedUser.getId());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/{customerKeyId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get customer key by id",
            notes = "Route allows fetching a single customer key by its id",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS)
    public CustomerKey getCustomerKeyById(
            @PathVariable(value = "customerKeyId") String encodedCustomerKeyId
    ) {
        return hashIdService.decode(encodedCustomerKeyId)
                .map(customerKeyId -> customerKeyService.findByCustomerKeyId(authorizedUser.getId(), customerKeyId))
                .orElseThrow(() -> new EntityNotFoundException(CustomerKey.class.getSimpleName(), encodedCustomerKeyId));
    }


    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Create a customer key",
            notes = "Route allows creating a customer key.",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS
    )
    public CustomerKey createCustomerKey(
            @Valid @RequestBody CustomerKeyPostRequest customerKeyRequest) {
        return customerKeyService.create(authorizedUser.getId(), customerKeyRequest);
    }


    @RequestMapping(
            method = RequestMethod.POST,
            path = "/{customerKeyId}/user-payment-options/{userPaymentOptionId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(
            value = "Add a user payment option to a customer key",
            notes = "Route allows adding a user payment option to a customer key.",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS
    )
    public CustomerKey addCustomerKeyPaymentOption(
            @ApiParam(value = "The internal customer key id, encoded.", example = SwaggerDocConstants.EXAMPLE_ID)
            @PathVariable(value = "customerKeyId") String encodedCustomerKeyId,
            @ApiParam(value = "The internal user payment option id, encoded.", example = SwaggerDocConstants.EXAMPLE_ID)
            @PathVariable(value = "userPaymentOptionId") String encodedUserPaymentOptionId) {

        final Long customerKeyId = hashIdService.decode(encodedCustomerKeyId).orElseThrow(() -> new EntityNotFoundException(CustomerKey.class.getSimpleName(), encodedCustomerKeyId));
        final Long userPaymentOptionId = hashIdService.decode(encodedUserPaymentOptionId).orElseThrow(() -> new EntityNotFoundException(UserPaymentOption.class.getSimpleName(), encodedCustomerKeyId));

        return customerKeyService.addUserPaymentOption(authorizedUser.getId(), customerKeyId, userPaymentOptionId);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/{customerKeyId}/user-payment-options/{userPaymentOptionId}"
    )
    @ApiOperation(
            value = "Delete a user payment option from a customer key",
            notes = "Route allows deleting a user payment option from a customer key.",
            tags = SwaggerConstant.TAGS_CUSTOMER_KEYS
    )
    public void deleteCustomerKeyPaymentOption(
            @ApiParam(value = "The internal customer key id, encoded.", example = SwaggerDocConstants.EXAMPLE_ID)
            @PathVariable(value = "customerKeyId") String encodedCustomerKeyId,
            @ApiParam(value = "The internal user payment option id, encoded.", example = SwaggerDocConstants.EXAMPLE_ID)
            @PathVariable(value = "userPaymentOptionId") String encodedUserPaymentOptionId) {

        final Long customerKeyId = hashIdService.decode(encodedCustomerKeyId).orElseThrow(() -> new EntityNotFoundException(CustomerKey.class.getSimpleName(), encodedCustomerKeyId));
        final Long userPaymentOptionId = hashIdService.decode(encodedUserPaymentOptionId).orElseThrow(() -> new EntityNotFoundException(UserPaymentOption.class.getSimpleName(), encodedCustomerKeyId));

        customerKeyService.deleteUserPaymentOption(authorizedUser.getId(), customerKeyId, userPaymentOptionId);
    }
}
