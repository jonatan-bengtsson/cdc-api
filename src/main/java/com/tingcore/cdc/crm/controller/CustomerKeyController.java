package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.response.CustomerKey;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@RestController
@RequestMapping(value = "/v1/customer-keys")
public class CustomerKeyController {

    private CustomerKeyService customerKeyService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CustomerKeyController(final CustomerKeyService customerKeyService) {
        this.customerKeyService = customerKeyService;
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
            tags = {SwaggerConstant.TAGS_CUSTOMER_KEYS, SwaggerConstant.TAGS_USERS})
    public PageResponse<CustomerKey> getCustomerKeys() {
        return customerKeyService.findByUserId(authorizedUser.getUser().getId());
    }
}
