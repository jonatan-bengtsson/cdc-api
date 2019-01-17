package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.cdc.service.TimeService;
import com.tingcore.emp.pricing.client.api.v1.PricingProfileRestApi;
import com.tingcore.emp.pricing.profile.Source;
import com.tingcore.emp.pricing.profile.response.PriceProfileAssociationResponse;
import com.tingcore.emp.pricing.profile.response.PriceProfileResponse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Test;
import retrofit2.HttpException;
import retrofit2.Response;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.tingcore.commons.core.test.JsonTestUtils.jsonFileToString;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdvancedPricingRepositoryTest {

    private static final long ORGANIZATION_ID = 22L;
    private static final String CURRENCY = "SEK";
    private static final Instant NOW = Instant.now();
    private static final ConnectorId CONNECTOR_ID = new ConnectorId(1L);
    private static final String PROFILE_NAME = "Some Profile";

    private PricingProfileRestApi api = mock(PricingProfileRestApi.class);
    private TimeService timeService = mock(TimeService.class);
    private AdvancedPricingRepository repository = new AdvancedPricingRepository(new ObjectMapper(), api, timeService, 1);

    @Before
    public void setUp() throws Exception {
        when(timeService.currentDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);
        when(timeService.currentTime()).thenReturn(LocalTime.MIN);
    }

    @Test
    public void singleConnector_singleAssociation_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, null)
                ));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void singleConnector_singleAssociation_advancedRule_happyPath_beforeMidday() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/time-restricted.json"), CURRENCY, NOW, null)
                ));
        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "1.00 SEK/min"));
    }

    @Test
    public void singleConnector_singleAssociation_advancedRule_happyPath_afterMidday() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/time-restricted.json"), CURRENCY, NOW, null)
                ));
        when(timeService.currentTime())
                .thenReturn(LocalTime.NOON.plusMinutes(30));
        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/h"));
    }

    @Test
    public void singleConnector_singleAssociation_simpleRule_failAssociationFetch() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(failAssociation(404));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleConnector_singleAssociation_simpleRule_failProfileFetch() {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(failProfile(404));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleConnector_multipleAssociation_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(asList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null),
                        new PriceProfileAssociationResponse("def", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.SMS, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void multipleConnector_singleAssociation_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null))))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("def", 2L, NOW, ORGANIZATION_ID, "zzz", NOW, null, Source.ALL, null))));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, null)));
        when(api.getPriceProfile(ORGANIZATION_ID, "zzz"))
                .thenReturn(completedFuture(new PriceProfileResponse("zzz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-energy-based.json"), CURRENCY, NOW, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).hasSize(2);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
        assertThat(result).contains(new ConnectorPrice(new ConnectorId(2L), "10.00 SEK/kWh"));
    }

    @Test
    public void multipleConnector_singleAssociation_simpleRule_failAssociationFetch() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null))))
                .thenReturn(failAssociation(500));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void multipleConnector_singleAssociation_simpleRule_failProfileFetch() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null, Source.ALL, null))))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("def", 2L, NOW, ORGANIZATION_ID, "zzz", NOW, null, Source.ALL, null))));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(failProfile(500));
        when(api.getPriceProfile(ORGANIZATION_ID, "zzz"))
                .thenReturn(completedFuture(new PriceProfileResponse("zzz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).hasSize(1);
        assertThat(result).contains(new ConnectorPrice(new ConnectorId(2L), "2.00 SEK/min"));
    }

    private CompletableFuture<List<PriceProfileAssociationResponse>> failAssociation(int code) {
        HttpException e = new HttpException(Response.error(code, ResponseBody.create(MediaType.parse("application/json"), "{}")));
        CompletableFuture<List<PriceProfileAssociationResponse>> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }

    private CompletableFuture<PriceProfileResponse> failProfile(int code) {
        HttpException e = new HttpException(Response.error(code, ResponseBody.create(MediaType.parse("application/json"), "{}")));
        CompletableFuture<PriceProfileResponse> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }
}
