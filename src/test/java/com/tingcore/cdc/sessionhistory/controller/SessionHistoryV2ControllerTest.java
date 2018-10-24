package com.tingcore.cdc.sessionhistory.controller;

import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.payments.history.v2.ApiChargeHistoryAdapter;
import com.tingcore.cdc.sessionhistory.service.SessionHistoryService;
import com.tingcore.sessions.history.api.v1.ApiSessionHistoryContainer;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.stream.Collectors;

import static com.tingcore.cdc.utils.JsonTestUtils.jsonFileToObject;
import static com.tingcore.cdc.utils.JsonTestUtils.jsonFileToString;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = SessionHistoryController.class)
public class SessionHistoryV2ControllerTest extends ControllerUnitTest {

    @MockBean
    private SessionHistoryService service;


    private static final String ONE_HASHED = "pNK7reEGPr";

    @Test
    public void testGetChargingKeyHistory() throws Exception {
        ApiSessionHistoryContainer original = jsonFileToObject("test-data/session-history/history.v2.json", ApiSessionHistoryContainer.class);
        when(service.getSessionHistory(anyLong(), anyLong(), anyLong()))
                .thenReturn(original
                        .getResult()
                        .stream()
                        .map(ApiChargeHistoryAdapter::new)
                        .collect(Collectors.toList()));

        MockHttpServletRequestBuilder request = get("/v1/session-history/customer-key/" + ONE_HASHED + "?fromInstant=0&toInstant=100");

        MvcResult response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String result = response.getResponse().getContentAsString();
        String expected = jsonFileToString("test-data/session-history/result.json");
        assertThatJson(result).isEqualTo(expected);
    }

}