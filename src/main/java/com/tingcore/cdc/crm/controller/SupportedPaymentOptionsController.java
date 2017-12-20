package com.tingcore.cdc.crm.controller;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.service.PaymentOptionService;
import com.tingcore.users.model.PaymentOptionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author palmithor
 * @since 2017-12-15
 */
@RestController
@RequestMapping(value = "/v1/supported-payment-options")
public class SupportedPaymentOptionsController {

    private PaymentOptionService paymentOptionService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public SupportedPaymentOptionsController(final PaymentOptionService paymentOptionService) {
        this.paymentOptionService = paymentOptionService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ApiOperation(value = "Get supported payment options",
            notes = "Route allows fetching the users's supported payment options.",
            tags = {SwaggerConstant.TAGS_CUSTOMER_KEYS, SwaggerConstant.TAGS_PAYMENT_OPTIONS})
    public List<PaymentOptionResponse> getSupportedPaymentOptions() {
        return paymentOptionService.findSupportedPaymentOptions(authorizedUser.getUser().getId());
    }
}
