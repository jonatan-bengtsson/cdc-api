package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.response.CustomerKeyResponse;
import com.tingcore.cdc.crm.response.GetUserResponse;
import com.tingcore.cdc.crm.service.CustomerKeyService;
import com.tingcore.cdc.crm.service.UserService;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final String PARAM_ID = "userId";
    private static final String PATH_PARAM_ID = "/{" + PARAM_ID + "}";


    private UserService userService;
    private CustomerKeyService customerKeyService;
    private HashIdService hashIdService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public UserController(final UserService userService, final CustomerKeyService customerKeyService, final HashIdService hashIdService) {
        this.userService = userService;
        this.customerKeyService = customerKeyService;
        this.hashIdService = hashIdService;
    }


    @RequestMapping(value = "/self", method = GET, produces = "application/json")
    @ApiOperation(value = "Get the authorized user",
            notes = "Route allows fetching the user that is currently logged in. " +
                    "The endpoint uses the authorization id affiliated with the user.",
            tags = SwaggerConstant.TAGS_USERS)
    public GetUserResponse getSelf(@RequestParam(value = "includeAttributes") Boolean includeAttributes) {
        final Long authorizedUserId = authorizedUser.getUser().getId();
        return userService.getUserById(authorizedUserId, authorizedUserId, includeAttributes);
    }

    @ApiResponses({
            // In order for the Swagger configuration to pick up ErrorResponse.class.getSimpleName() and populate it with a
            // certain class it has to be defined at least for one route. This route was chosen.
            // https://github.com/springfox/springfox/issues/735
            @ApiResponse(code = SwaggerDefaultConstant.HTTP_STATUS_NOT_FOUND, message = SwaggerDefaultConstant.MESSAGE_NOT_FOUND, response = ErrorResponse.class)
    })
    @RequestMapping(
            value = "/self/customer-keys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get customer keys by user id",
            notes = "Route allows fetching all customer keys that belong to a user.",
            tags = {SwaggerConstant.TAGS_CUSTOMER_KEYS, SwaggerConstant.TAGS_USERS})
    public PageResponse<CustomerKeyResponse> getCustomerKeys() {
        return customerKeyService.findByUserId(authorizedUser.getUser().getId());
    }
}
