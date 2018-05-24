package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.PrivacyPolicyApproval;
import com.tingcore.cdc.crm.service.v2.AgreementService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.api.service.HashIdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author palmithor
 * @since 2018-05-22
 */
@RestController
@RequestMapping(value = "/v2/privacy-policies")
@Validated
public class PrivacyPolicyController {

    private final AgreementService agreementService;
    private final HashIdService hashIdService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public PrivacyPolicyController(final AgreementService agreementService, final HashIdService hashIdService) {
        this.agreementService = agreementService;
        this.hashIdService = hashIdService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/current")
    @ApiOperation(value = "Check if user has approved current",
            notes = "Route allows to check if the logged in user has approved the current privacy policy.",
            tags = SwaggerDocConstants.TAGS_PRIVACY_POLICIES)
    public PrivacyPolicyApproval getCurrentPrivacyPolicyApproval() {
        return agreementService.getCurrentPrivacyPolicyApproval(authorizedUser.getId(), authorizedUser.getOrganization().getId());
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/approvals/{id}")
    @ApiOperation(value = "Approve the given agreement",
            notes = "Route allows the logged in user to approve a given agreement.",
            tags = SwaggerDocConstants.TAGS_PRIVACY_POLICIES)
    public void approvePrivacyPolicy(@PathVariable("id") final String encodedId) {
        final Long agreementId = hashIdService.decode(encodedId).orElseThrow(() -> new EntityNotFoundException("Could not find the specified agreement."));
        agreementService.approvePrivacyPolicy(authorizedUser.getId(), agreementId);
    }

}
