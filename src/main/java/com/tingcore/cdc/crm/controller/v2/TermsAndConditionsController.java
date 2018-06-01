package com.tingcore.cdc.crm.controller.v2;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.cdc.crm.service.v2.AgreementService;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.commons.hash.HashIdService;
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
@RequestMapping(value = "/v2/terms-and-conditions")
@Validated
public class TermsAndConditionsController {

    private final AgreementService agreementService;
    private final HashIdService hashIdService;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    public TermsAndConditionsController(final AgreementService agreementService, final HashIdService hashIdService) {
        this.agreementService = agreementService;
        this.hashIdService = hashIdService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/current")
    @ApiOperation(value = "Check if user has approved current",
            notes = "Route allows to check if the logged in user has approved the current privacy policy.",
            tags = SwaggerDocConstants.TAGS_TERMS_AND_CONDITIONS)
    public TermsAndConditionsApproval getCurrentTermsAndConditionsApproval() {
        return agreementService.getCurrentTermsAndConditionsApproval(authorizedUser.getId(), authorizedUser.getOrganization().getId());
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/approvals/{id}")
    @ApiOperation(value = "Approve the given agreement",
            notes = "Route allows the logged in user to approve a given agreement.",
            tags = SwaggerDocConstants.TAGS_TERMS_AND_CONDITIONS)
    public void approveTermsAndConditions(@PathVariable("id") final String encodedId) {
        final Long agreementId = hashIdService.decode(encodedId).orElseThrow(() -> new EntityNotFoundException("Could not find the specified agreement."));
        agreementService.approveTermsAndConditions(authorizedUser.getId(), agreementId);
    }

}
