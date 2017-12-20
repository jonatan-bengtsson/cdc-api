package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.response.UserPaymentOption;
import com.tingcore.cdc.crm.service.PaymentOptionService;
import com.tingcore.commons.rest.PageResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@RestController
@RequestMapping(value = "/v1/payment-options")
public class PaymentOptionsController {

    private PaymentOptionService paymentOptionService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public PaymentOptionsController(final PaymentOptionService paymentOptionService) {
        this.paymentOptionService = paymentOptionService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get payment options",
            notes = "Route allows fetching the payment options the user has set up.",
            tags = SwaggerConstant.TAGS_PAYMENT_OPTIONS)
    public PageResponse<UserPaymentOption> getUserPaymentOptions() {
        return paymentOptionService.findUserPaymentOptions(authorizedUser.getUser().getId());
    }
}
