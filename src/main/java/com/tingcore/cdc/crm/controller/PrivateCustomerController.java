package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.request.UpdatePrivateCustomerRequest;
import com.tingcore.cdc.crm.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = "/v1/private-customers")
public class PrivateCustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateCustomerController.class);

    private UserService userService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public PrivateCustomerController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value="/self", method = PUT, produces = "application/json")
    @ApiOperation(value = "Create or update a private customer's attribute values",
            notes = "Route allows creating new or updating an existing private customer's attribute values",
            tags = SwaggerConstant.TAGS_USERS)
    public User updatePrivateCustomerAttributeValues(@Valid @RequestBody UpdatePrivateCustomerRequest userRequest) {
        final Long authorizedUserId = authorizedUser.getUser().getId();
        return userService.putUserAttributeValues(authorizedUserId, userRequest);
    }
}
