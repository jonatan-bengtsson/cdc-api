package com.tingcore.cdc.payments.controller;

import com.tingcore.cdc.exception.PaymentAccountFailureException;
import com.tingcore.cdc.payments.api.ApiCreateAccountRequest;
import com.tingcore.cdc.payments.service.PaymentAccountService;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.payments.emp.model.ApiPaymentAccount;
import com.tingcore.payments.emp.model.Card;
import com.tingcore.payments.emp.model.DeletedCustomer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
@RequestMapping("/" + PaymentAccountController.VERSION + "/" + PaymentAccountController.ACCOUNTS)
public class PaymentAccountController {
    static final String VERSION = "v1";
    static final String ACCOUNTS = "paymentaccounts";
    static final String USERS = "users";
    static final String CARD = "card";

    private final PaymentAccountService paymentAccountService;
    private HashIdService hashIdService;

    public PaymentAccountController(final PaymentAccountService paymentAccountService,
                                    final HashIdService hashIdService) {
        this.paymentAccountService = notNull(paymentAccountService);
        this.hashIdService = notNull(hashIdService);
    }

    @PostMapping("/" + USERS)
    @ApiOperation(code = 201, value = "Create a user payment account", response = ApiPaymentAccount.class, tags = {ACCOUNTS})
    public ApiPaymentAccount createUserAccount(final @RequestBody @Valid ApiCreateAccountRequest request) {
        return hashIdService.decode(request.getAccount().getAccountOwnerId())
                .map(longId -> paymentAccountService.createUserAccount(longId, request))
                .orElseThrow(() -> new PaymentAccountFailureException(request.getAccount().getAccountOwnerId()));
    }

    @GetMapping("/" + USERS + "/{paymentOptionReference}")
    @ApiOperation(value = "Get a users payment account.", response = ApiPaymentAccount.class, tags = {ACCOUNTS})
    public ApiPaymentAccount getUserAccount(final @PathVariable("paymentOptionReference") @NotNull String paymentOptionReference) {
        return paymentAccountService.getAccount(paymentOptionReference);

    }

    @DeleteMapping("/" + USERS + "/{paymentOptionReference}")
    @ApiOperation(code = 204, value = "Delete a users payment account.", response = DeletedCustomer.class, tags = {ACCOUNTS})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Could not parse the request.", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Payment account with the supplied id was not found.", response = ErrorResponse.class)
    })
    public DeletedCustomer deleteUserAccount(final @PathVariable("paymentOptionReference") @NotNull String strPaymentOption) {
        return paymentAccountService.deleteUserAccount(strPaymentOption);
    }

    @GetMapping("/" + USERS)
    @ApiOperation(value = "Get a users payment accounts.", response = ApiPaymentAccount.class, responseContainer = "List", tags = {ACCOUNTS})
    public List<ApiPaymentAccount> getUserPaymentAccounts(final @RequestParam(value = "keyId", required = false) String keyId,
                                                          final @RequestParam(value = "userId", required = false) String userId) {
        Long key = null;
        Long user = null;

        if (keyId != null) {
            key = hashIdService.decode(keyId)
                    .orElseThrow(() -> new PaymentAccountFailureException(keyId));
        }
        if (userId != null) {
            user = hashIdService.decode(userId)
                    .orElseThrow(() -> new PaymentAccountFailureException(userId));
        }
        return paymentAccountService.getAllAccountsById(key, user);
    }

    @GetMapping("/" + USERS + "/{stripetoken}" + "/" + CARD)
    @ApiOperation(value = "Get card details from stripe", response = Card.class, tags = {ACCOUNTS})
    public Card getCardInformation(@PathVariable("stripetoken") final String stripeToken) {
        return paymentAccountService.getCardInformation(stripeToken);
    }
}
