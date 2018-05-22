package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.User;
import com.tingcore.cdc.crm.service.v1.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author palmithor
 * @since 2017-11-09
 */
@RestController("UserControllerV1")
@RequestMapping(value = "/v1/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public UserController(final UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/self", method = GET, produces = "application/json")
    @ApiOperation(value = "Get the authorized user",
            notes = "Route allows fetching the user that is currently logged in. " +
                    "The endpoint uses the authorization id affiliated with the user.",
            tags = SwaggerDocConstants.TAGS_USERS)
    public User getSelf(@RequestParam(value = "includeAttributes") Boolean includeAttributes) {
        return userService.getUserById(authorizedUser.getId(), includeAttributes);
    }

}
