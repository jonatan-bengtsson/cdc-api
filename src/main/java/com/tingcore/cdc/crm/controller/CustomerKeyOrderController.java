package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.model.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.cdc.crm.service.CustomerKeyOrderService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.customerkeyorder.client.model.response.Order;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/customer-keys-orders")
public class CustomerKeyOrderController {

    private final CustomerKeyOrderService customerKeyOrderService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CustomerKeyOrderController(final CustomerKeyOrderService customerKeyOrderService) {
        this.customerKeyOrderService = customerKeyOrderService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Create customer key order",
            notes = "Route allows to create a customer key order on behalf of the user logged in",
            tags = SwaggerConstant.TAGS_CUSTOMER_KEY_ORDERS)
    public Order createCustomerKeyOrder(@Valid @RequestBody CustomerKeyOrderRequest request) {
        if(authorizedUser.getOrganization() == null) {
            throw new CustomerKeyOrderServiceException(ErrorResponse.badRequest().message("Current user has no organization").build());
        }
        return customerKeyOrderService.createOrder(authorizedUser.getId(), authorizedUser.getOrganization().getId(), request);
    }

}
