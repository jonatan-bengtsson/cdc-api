package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.service.v2.CustomerService;
import com.tingcore.users.model.v2.response.Customer;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Validated
@RequestMapping(value = "/v2/customers")
public class CustomerController {

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/self", method = GET)
    @ApiOperation(value = "Get the authorized Customer",
            notes = "Route allows fetching the authorized customer",
            tags = SwaggerDocConstants.TAGS_CUSTOMERS)
    public Customer getCustomerById() {
        return customerService.findById(authorizedUser.getId());
    }

}
