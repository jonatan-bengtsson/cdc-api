package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.exception.PaymentAccountFailureException;
import com.tingcore.cdc.payments.api.ApiCreateAccountRequest;
import com.tingcore.cdc.payments.api.ApiStripeCustomer;
import com.tingcore.cdc.payments.service.v1.PaymentAccountService;
import com.tingcore.commons.hash.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.payments.cpo.model.ApiDeletedCustomer;
import com.tingcore.payments.cpo.model.ApiElwinCustomerContract;
import com.tingcore.payments.cpo.model.ApiPaymentAccount;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + PaymentAccountController.VERSION + "/" + PaymentAccountController.ACCOUNTS)
public class PaymentAccountController {
    static final String VERSION = "v1";
    static final String ACCOUNTS = "payment-accounts";
    static final String USERS = "users";
    static final String PAYMENT_OPTIONS = "payment-options";

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    private final PaymentAccountService paymentAccountService;
    private HashIdService hashIdService;

    public PaymentAccountController(final PaymentAccountService paymentAccountService,
                                    final HashIdService hashIdService) {
        this.paymentAccountService = notNull(paymentAccountService);
        this.hashIdService = notNull(hashIdService);
    }

    @PostMapping("/" + USERS)
    @ApiOperation(code = 201, value = "Create a user payment account", response = ApiPaymentAccount.class, tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public ApiPaymentAccount createUserAccount(final @RequestBody @Valid ApiCreateAccountRequest request) {

      // Decoding for payment-type specific fields
      switch (request.getAccount().getPaymentMethod()) {
        case PREPAID:
          break;
        case STRIPE:
          request.getAccount().getData().computeIfPresent("organizationId", (k,v) -> hashIdService.decode((String)v).get());
          break;
        case ELWIN:
          request.getAccount().getData().computeIfPresent("organizationId", (k,v) -> hashIdService.decode((String)v).get());
          break;
      }

      return hashIdService.decode(request.getAccount().getAccountOwnerId())
                .map(longId -> paymentAccountService.createUserAccount(longId, request))
                .orElseThrow(() -> new PaymentAccountFailureException(request.getAccount().getAccountOwnerId()));
    }

    @PostMapping("/" + USERS + "/stripe")
    @ApiOperation(code = 201, value = "Create a stripe customer", response = ApiStripeCustomer.class, tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public ApiStripeCustomer createStripeCustomer(final @RequestParam("cardSource") @NotNull String cardSource,
                                                  final @RequestParam("organizationId") @NotNull String organizationId) {
        return hashIdService.decode(organizationId)
                .map(id -> paymentAccountService.createStripeCustomer(cardSource, id))
                .map(this::toApiCustomer)
                .orElseThrow(() -> new PaymentAccountFailureException(cardSource));
    }

    private ApiStripeCustomer toApiCustomer(final String customer) {
        return new ApiStripeCustomer(customer);
    }

    @GetMapping("/" + USERS + "/{paymentOptionReference}")
    @ApiOperation(value = "Get a users payment account.", response = ApiPaymentAccount.class, tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public ApiPaymentAccount getUserAccount(final @PathVariable("paymentOptionReference") @NotNull String paymentOptionReference) {
        return paymentAccountService.getAccount(paymentOptionReference);
    }

    @DeleteMapping("/" + PAYMENT_OPTIONS + "/{paymentOption}")
    @ApiOperation(value = "Delete a users payment account.", response = ApiDeletedCustomer.class, tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Could not parse the request.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Payment account with the supplied id was not found.", response = ErrorResponse.class)
    })
    public ApiDeletedCustomer deleteUserAccount(@PathVariable("paymentOption") String paymentOption) {
        return paymentAccountService.deleteUserAccount(authorizedUser.getId(), paymentOption);
    }

    @GetMapping("/" + USERS)
    @ApiOperation(value = "Get a users payment accounts.", response = ApiPaymentAccount.class, responseContainer = "List", tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public List<ApiPaymentAccount> getUserPaymentAccounts(final @RequestParam(value = "keyId", required = false) String keyId) {
        Long key = null;
        Long user = null;

        if (keyId != null) {
            key = hashIdService.decode(keyId)
                    .orElseThrow(() -> new PaymentAccountFailureException(keyId));
        } else {
            user = authorizedUser.getId();
        }

        return paymentAccountService.getAllAccountsById(key, user);
    }

    @GetMapping("/" + USERS + "/elwin/{elwinId}/contracts")
    @ApiOperation(value = "Get elwin customer contracts", response = ApiElwinCustomerContract.class, responseContainer = "List", tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public List<ApiElwinCustomerContract> getElwinContracts(@PathVariable("elwinId") @NotNull String elwinId) {
        return paymentAccountService.getElwinContracts(elwinId);
    }

    @GetMapping("/" + USERS + "/elwin/{elwinId}/contracts/{keyIdentifier}")
    @ApiOperation(value = "Get elwin customer contracts", response = ApiElwinCustomerContract.class, tags = {SwaggerDocConstants.TAGS_PAYMENT_ACCOUNTS})
    public ApiElwinCustomerContract getElwinContracts(@PathVariable("elwinId") @NotNull String elwinId, @PathVariable("keyIdentifier") @NotNull String keyIdentifier) {
        return paymentAccountService.getElwinContract(elwinId, keyIdentifier);
    }
}
