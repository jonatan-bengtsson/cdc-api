package com.tingcore.cdc.crm.controller.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tingcore.cdc.ControllerFunctionalTest;
import com.tingcore.cdc.crm.model.TermsAndConditionsApproval;
import com.tingcore.commons.core.utils.JsonUtils;
import com.tingcore.commons.rest.ErrorResponse;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.tingcore.cdc.crm.utils.AgreementDataUtils.getValidJson;
import static org.assertj.core.api.Assertions.assertThat;


public class TermsAndConditionsControllerFT extends ControllerFunctionalTest {

    @Test
    public void getCurrentTermsAndConditionsApproval_isApproved() {
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(200).setBody(getValidJson(Boolean.TRUE)));
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(200).setBody(getValidJson(Boolean.TRUE)));
        final ResponseEntity<TermsAndConditionsApproval> responseEntity = restTemplate.exchange("/v2/terms-and-conditions/current",
                HttpMethod.GET, new HttpEntity<>(createAuthHeaders()), TermsAndConditionsApproval.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getApproved()).isTrue();
    }

    @Test
    public void getCurrentTermsAndConditionsApproval_isNotApproved() {
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(200).setBody(getValidJson(Boolean.TRUE)));
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(404));
        final ResponseEntity<TermsAndConditionsApproval> responseEntity = restTemplate.exchange("/v2/terms-and-conditions/current",
                HttpMethod.GET, new HttpEntity<>(createAuthHeaders()), TermsAndConditionsApproval.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getApproved()).isFalse();
    }

    @Test
    public void getCurrentTermsAndConditionsApproval_isNotFound() throws JsonProcessingException {
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(404).setBody(JsonUtils.getObjectMapper().writeValueAsString(ErrorResponse.notFound().build())));
        final ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange("/v2/terms-and-conditions/current",
                HttpMethod.GET, new HttpEntity<>(createAuthHeaders()), ErrorResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getCurrentTermsAndConditionsApproval_unexpectedErrorGetCurrent() throws JsonProcessingException {
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(500).setBody(JsonUtils.getObjectMapper().writeValueAsString(ErrorResponse.serverError().build())));
        final ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange("/v2/terms-and-conditions/current",
                HttpMethod.GET, new HttpEntity<>(createAuthHeaders()), ErrorResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void getCurrentTermsAndConditionsApproval_unexpectedErrorGetApproved() throws JsonProcessingException {
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(200).setBody(getValidJson(Boolean.TRUE)));
        userServiceMockServer.enqueue(new MockResponse().setResponseCode(500).setBody(JsonUtils.getObjectMapper().writeValueAsString(ErrorResponse.serverError().build())));
        final ResponseEntity<ErrorResponse> responseEntity = restTemplate.exchange("/v2/terms-and-conditions/current",
                HttpMethod.GET, new HttpEntity<>(createAuthHeaders()), ErrorResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
