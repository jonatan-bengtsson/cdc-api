package com.tingcore.cdc;


import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.JacksonConfiguration;
import com.tingcore.cdc.configuration.LocaleConfiguration;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.service.MessageByLocaleService;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.MockMvcUtils;
import com.tingcore.commons.api.crm.model.Organization;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.users.model.v1.response.UserResponse;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author palmithor
 * @since 2017-09-14
 */
@RunWith(SpringRunner.class)
@ImportAutoConfiguration({LocaleConfiguration.class, JacksonConfiguration.class})
@Import({MessageByLocaleService.class, HashIdService.class, MockMvcUtils.class})
@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public abstract class ControllerUnitTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected MockMvcUtils mockMvcUtils;
    @Autowired protected HashIdService hashIdService;
    @MockBean protected AuthorizedUser authorizedUser;

    @Before
    public void setUp() {
        final UserResponse authorizedUserMock = mock(UserResponse.class);
        when(authorizedUserMock.getId()).thenReturn(CommonDataUtils.getNextId());
        final Long authorizedUserId = CommonDataUtils.getNextId();
        final String encodedAuthorizedUserId = hashIdService.encode(authorizedUserId);

        given(authorizedUser.getId()).willReturn(authorizedUserId);
        given(authorizedUser.getEncodedId()).willReturn(encodedAuthorizedUserId);
    }

    protected void mockAuthorizedUserOrganization() {
        final Organization organizationMock = mock(Organization.class);
        when(organizationMock.getId()).thenReturn(CommonDataUtils.getNextId());
        given(authorizedUser.getOrganization()).willReturn(organizationMock);
    }
}

