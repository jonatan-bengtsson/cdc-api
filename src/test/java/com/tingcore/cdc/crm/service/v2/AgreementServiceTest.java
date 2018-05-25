package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.model.PrivacyPolicyApproval;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.cdc.crm.repository.v2.PrivacyPolicyRepository;
import com.tingcore.cdc.crm.repository.v2.TermsAndConditionRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;


/**
 * @author palmithor
 * @since 2018-05-24
 */
@RunWith(SpringRunner.class)
public class AgreementServiceTest {

    @MockBean private PrivacyPolicyRepository privacyPolicyRepository;
    @MockBean private TermsAndConditionRepository termsAndConditionRepository;
    private AgreementService service;

    @Before
    public void setUp() {
        this.service = new AgreementService(privacyPolicyRepository, termsAndConditionRepository);
    }

    @Test
    public void getCurrentPrivacyPolicyApproval() {
        given(privacyPolicyRepository.getCurrentApproval(anyLong(), anyLong())).willReturn(new ApiResponse<>(mock(PrivacyPolicyApproval.class)));
        assertThatCode(() -> service.getCurrentPrivacyPolicyApproval(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()))
                .doesNotThrowAnyException();
    }

    @Test
    public void failGetCurrentPrivacyPolicyApproval() {
        given(privacyPolicyRepository.getCurrentApproval(anyLong(), anyLong())).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.getCurrentPrivacyPolicyApproval(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()));
    }

    @Test
    public void approvePrivacyPolicy() {
        given(privacyPolicyRepository.approve(anyLong(), anyLong())).willReturn(new ApiResponse<>((Void) null));
        assertThatCode(() -> service.approvePrivacyPolicy(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()))
                .doesNotThrowAnyException();
    }

    @Test
    public void failApprovePrivacyPolicy() {
        given(privacyPolicyRepository.approve(anyLong(), anyLong())).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.approvePrivacyPolicy(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()));
    }

    @Test
    public void getCurrentTermsAndConditionApproval() {
        given(termsAndConditionRepository.getCurrentApproval(anyLong(), anyLong())).willReturn(new ApiResponse<>(mock(TermsAndConditionsApproval.class)));
        assertThatCode(() -> service.getCurrentTermsAndConditionsApproval(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()))
                .doesNotThrowAnyException();
    }

    @Test
    public void failGetCurrentTermsAndConditionsApproval() {
        given(termsAndConditionRepository.getCurrentApproval(anyLong(), anyLong())).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.getCurrentTermsAndConditionsApproval(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()));
    }


    @Test
    public void approveTermsAndConditions() {
        given(termsAndConditionRepository.approve(anyLong(), anyLong())).willReturn(new ApiResponse<>((Void) null));
        assertThatCode(() -> service.approveTermsAndConditions(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()))
                .doesNotThrowAnyException();
    }

    @Test
    public void failApproveTermsAndConditions() {
        given(termsAndConditionRepository.approve(anyLong(), anyLong())).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> service.approveTermsAndConditions(CommonDataUtils.getNextId(), CommonDataUtils.getNextId()));
    }
}