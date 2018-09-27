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
@RequestMapping(value = "/public/v2/privacy-policies")
public class PublicPrivacyPolicyController {

    private final AgreementService agreementService;

    public PublicPrivacyPolicyController(final AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userPrefix}")
    @ApiOperation(value = "Get the latest privacy policy",
            notes = "Route allows fetching the active privacy policy for an organization.",
            tags = SwaggerDocConstants.TAGS_PRIVACY_POLICIES)
    public Agreement getPrivacyPolicyByUserPrefix(@PathVariable("userPrefix") final String userPrefix){
        return agreementService.getPrivacyPolicyByUserPrefix(userPrefix);
    }

}
