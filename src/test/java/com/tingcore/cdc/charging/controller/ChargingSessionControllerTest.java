package com.tingcore.cdc.charging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.tingcore.cdc.ControllerUnitTest;
import com.tingcore.cdc.charging.model.ChargingSession;
import com.tingcore.cdc.charging.model.ChargingSessionId;
import com.tingcore.cdc.charging.model.ChargingSessionStatus;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.service.ChargingSessionService;
import com.tingcore.cdc.service.TimeService;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ChargingSessionController.class)
public class ChargingSessionControllerTest extends ControllerUnitTest {

    @MockBean
    private ChargingSessionService chargingSessionService;
    @MockBean
    private TimeService timeService;

    @Test
    public void testCutOff() throws Exception {
        final Instant now = Instant.parse("2018-08-28T11:30:00.00Z");
        given(timeService.now()).willReturn(now);
        given(chargingSessionService.fetchOngoingSessions(any())).willReturn(asList(
                new ChargingSession(new ChargingSessionId(121L), new CustomerKeyId(123L), null, now.minus(100L, HOURS), null, ChargingSessionStatus.STARTED, null, null, null),
                new ChargingSession(new ChargingSessionId(122L), new CustomerKeyId(123L), null, now.minus(24L * 60 - 1, MINUTES), null, ChargingSessionStatus.STARTED, null, null, null),
                new ChargingSession(new ChargingSessionId(123L), new CustomerKeyId(123L), null, now.minus(24L * 60 + 1, MINUTES), null, ChargingSessionStatus.STARTED, null, null, null),
                new ChargingSession(new ChargingSessionId(124L), new CustomerKeyId(123L), null, now.minus(1, MINUTES), null, ChargingSessionStatus.STARTED, null, null, null),
                // won't filter out sessions starting in the future
                new ChargingSession(new ChargingSessionId(125L), new CustomerKeyId(123L), null, now.plus(1, MINUTES), null, ChargingSessionStatus.STARTED, null, null, null)
        ));

        MockHttpServletRequestBuilder request = get("/v1/charging-sessions")
                .contentType(MediaType.APPLICATION_JSON_UTF8);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode list = (ArrayNode) mapper.readTree(json);
        assertThat(list).hasSize(3);
        assertThat(list.get(0).get("chargingSessionId").asText()).isEqualTo("YKMj629jzN");
        assertThat(list.get(1).get("chargingSessionId").asText()).isEqualTo("Lx5jLqMn1X");
        assertThat(list.get(2).get("chargingSessionId").asText()).isEqualTo("Md4ndbY7Jg");
    }
}
