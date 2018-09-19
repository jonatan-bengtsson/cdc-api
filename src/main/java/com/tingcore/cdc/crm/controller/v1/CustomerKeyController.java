package com.tingcore.cdc.crm.controller.v1;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.CustomerKey;
import com.tingcore.cdc.crm.model.UserPaymentOption;
import com.tingcore.cdc.crm.service.v1.CustomerKeyService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.payments.repository.PaymentAccountApiException;
import com.tingcore.cdc.payments.service.PaymentAccountService;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.PageResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import com.tingcore.payments.cpo.model.ApiElwinCustomerContract;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@RestController
@RequestMapping(value = "/v1/customer-keys")
public class CustomerKeyController {

    private final CustomerKeyService customerKeyService;
    private final PaymentAccountService paymentAccountService;
    private final HashIdService hashIdService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public CustomerKeyController(final CustomerKeyService customerKeyService,
                                 final PaymentAccountService paymentAccountService,
                                 final HashIdService hashIdService) {
        this.customerKeyService = customerKeyService;
        this.paymentAccountService = paymentAccountService;
        this.hashIdService = hashIdService;
    }

    @ApiResponses({
            // In order for the Swagger configuration to pick up ErrorResponse.class.getSimpleName() and populate it with a
            // certain class it has to be defined at least for one route. This route was chosen.
            // https://github.com/springfox/springfox/issues/735
            @ApiResponse(code = SwaggerDefaultConstant.HTTP_STATUS_NOT_FOUND, message = SwaggerDefaultConstant.MESSAGE_NOT_FOUND, response = ErrorResponse.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Get customer keys",
            notes = "Route allows fetching the authorized user's customer keys",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS)
    public PageResponse<CustomerKey> getCustomerKeys() {
        return customerKeyService.findByUserId(authorizedUser.getId());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{customerKeyId}")
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

    @RequestMapping(method = RequestMethod.POST, path = "/{customerKeyId}/user-payment-options/{userPaymentOptionId}")
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

        CustomerKey customerKey = customerKeyService.addUserPaymentOption(authorizedUser.getId(), customerKeyId, userPaymentOptionId);

        Optional<UserPaymentOption> connectedPaymentOption = customerKey.getUserPaymentOptions().stream()
                .filter(userPaymentOption -> userPaymentOption.getId().equals(userPaymentOptionId))
                .findFirst();

        if (connectedPaymentOption.isPresent() && connectedPaymentOption.get().getPaymentOptionReference().startsWith("ELWIN-")) {

            String keyIdentifier = customerKey.getKeyIdentifier();
            String elwinId = connectedPaymentOption.get().getPaymentOptionReference().split("-")[1];

            try {
                paymentAccountService.getElwinContract(elwinId, keyIdentifier);
            } catch (PaymentAccountApiException e) {
                if (e.getErrorResponse().getStatusCode() == 404) {
                    paymentAccountService.createElwinContract(elwinId, keyIdentifier);
                } else {
                    e.printStackTrace();
                    throw e;
                }
            }
        }

        return customerKey;

    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{customerKeyId}/user-payment-options/{userPaymentOptionId}")
    @ApiOperation(
            value = "Delete a user payment option from a customer key",
            notes = "Route allows deleting a user payment option from a customer key.",
            tags = SwaggerDocConstants.TAGS_CUSTOMER_KEYS
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
