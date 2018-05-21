package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.CustomerKeyOrderServiceException;
import com.tingcore.cdc.crm.request.CustomerKeyOrderRequest;
import com.tingcore.cdc.crm.service.CustomerKeyOrderService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.customerkeyorder.client.model.response.CustomerKeyOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/customer-key-orders")
public class CustomerKeyOrderController {

    private final CustomerKeyOrderService customerKeyOrderService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CustomerKeyOrderController(final CustomerKeyOrderService customerKeyOrderService) {
        this.customerKeyOrderService = customerKeyOrderService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create customer key order",
            notes = "Route allows to create a customer key order on behalf of the user logged in",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEY_ORDERS)
    public CustomerKeyOrder createCustomerKeyOrder(@Valid @RequestBody CustomerKeyOrderRequest request) {
        if(authorizedUser.getOrganization() == null) {
            throw new CustomerKeyOrderServiceException(ErrorResponse.badRequest().message("Current user has no organization").build());
        }
        return customerKeyOrderService.createOrder(authorizedUser.getId(), authorizedUser.getOrganization().getId(), request);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get user's customer key orders",
            notes = "Route allows to retrieve customer key orders on behalf of the user logged in",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEY_ORDERS)
    public PageResponse<CustomerKeyOrder> getUserCustomerKeyOrders() {
        List<CustomerKeyOrder> orders = customerKeyOrderService.findOrdersByUserId(authorizedUser.getId());
        return new PageResponse<>(orders);
    }

}
