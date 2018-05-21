package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.CustomerKeyType;
import com.tingcore.cdc.crm.service.v1.CustomerKeyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author palmithor
 * @since 2018-01-11
 */
@RestController
@RequestMapping(value = "/v1/customer-key-types")
public class CustomerKeyTypesController {

    private final CustomerKeyService customerKeyService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    @Autowired
    public CustomerKeyTypesController(final CustomerKeyService customerKeyService) {
        this.customerKeyService = customerKeyService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get all customer key types",
            notes = "Route allows fetching all customer key types.",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS)
    public List<CustomerKeyType> getCustomerKeyTypes() {
        return customerKeyService.getCustomerKeyTypes();
    }


}
