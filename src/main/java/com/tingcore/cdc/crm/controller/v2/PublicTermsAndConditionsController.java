package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.service.v2.AgreementService;
import com.tingcore.users.model.v2.response.Agreement;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/v2/terms-and-conditions")
public class PublicTermsAndConditionsController {

    private final AgreementService agreementService;

    public PublicTermsAndConditionsController(final AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userPrefix}")
    @ApiOperation(value = "Get the latest terms and conditions",
            notes = "Route allows fetching the active terms and conditions for an organization.",
            tags = SwaggerDocConstants.TAGS_TERMS_AND_CONDITIONS)
    public Agreement getTermsAndConditionsByUserPrefix(@PathVariable("userPrefix") final String userPrefix){
        return agreementService.getTermsAndConditionsByUserPrefix(userPrefix);
    }

}
