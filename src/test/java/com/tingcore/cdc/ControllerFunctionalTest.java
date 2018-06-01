package com.tingcore.cdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.cdc.utils.MockMvcUtils;
import com.tingcore.commons.api.model.Organization;
import com.tingcore.commons.api.security.ClaimsHeader;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.commons.hash.HashIdService;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Collections;

@SuppressWarnings(SuppressWarningConstant.CONSTANT_CONDITIONS)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportAutoConfiguration(UserServiceRepositoryConfiguration.class)
@ActiveProfiles(SpringProfilesConstant.FUNCTIONAL_TEST)
public abstract class ControllerFunctionalTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerFunctionalTest.class);

    protected static MockWebServer userServiceMockServer;

    static {
        userServiceMockServer = new MockWebServer();
        try {
            userServiceMockServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired public TestRestTemplate restTemplate;
    @Autowired public HashIdService hashIdService;
    @Autowired public ObjectMapper objectMapper;
    @Autowired public MockMvcUtils mockMvcUtils;

    public MultiValueMap<String, String> createAuthHeaders(final Long userId, final Long organizationId) {
        try {
            final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.put("content-type", Collections.singletonList(MediaType.APPLICATION_JSON_UTF8_VALUE));
            headers.put(ClaimsHeader.HEADER_CLAIM_USER_ID, Collections.singletonList(hashIdService.encode(userId)));
            // as the object mapper does have the id serializer this should work
            headers.put(ClaimsHeader.HEADER_CLAIM_ORGANIZATION, Collections.singletonList(objectMapper.writeValueAsString(Organization.createBuilder().id(organizationId).build())));
            headers.put(ClaimsHeader.HEADER_CLAIM_COGNITO_USERNAME, Collections.singletonList(CommonDataUtils.randomUUID()));
            return headers;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MultiValueMap<String, String> createAuthHeaders() {
        return createAuthHeaders(CommonDataUtils.getNextId(), CommonDataUtils.getNextId());
    }
}
