package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.cdc.charging.model.ConnectorId;
import com.tingcore.cdc.charging.model.ConnectorPrice;
import com.tingcore.cdc.service.TimeService;
import com.tingcore.commons.rest.ErrorResponse;
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
    private static final String FREE_OF_CHARGE = "free of charge";

    private PricingProfileRestApi api = mock(PricingProfileRestApi.class);
    private TimeService timeService = mock(TimeService.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    private AdvancedPricingRepository repository = new AdvancedPricingRepository(objectMapper, api, timeService, 1);

    @Before
    public void setUp() {
        when(timeService.currentDayOfWeek()).thenReturn(DayOfWeek.FRIDAY);
        when(timeService.currentTime()).thenReturn(LocalTime.MIN);
    }

    @Test
    public void singleCon_singleAs_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)
                ));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void singleCon_singleAs_advancedRule_happyPath_beforeMidday() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/time-restricted.json"), CURRENCY, NOW, Source.ALL, null, null)
                ));
        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "1.00 SEK/min"));
    }

    @Test
    public void singleCon_singleAs_advancedRule_happyPath_afterMidday() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/time-restricted.json"), CURRENCY, NOW, Source.ALL, null, null)
                ));
        when(timeService.currentTime())
                .thenReturn(LocalTime.NOON.plusMinutes(30));
        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/h"));
    }

    @Test
    public void singleCon_singleAs_simpleRule_Sms() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.SMS, null, null)
                ));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleCon_multipleAs_simpleRule_SmsAndAll() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(asList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null),
                        new PriceProfileAssociationResponse("def", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "aaa", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.SMS, null, null)
                ));
        when(api.getPriceProfile(ORGANIZATION_ID, "aaa"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("aaa", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)
                ));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void singleCon_multipleAs_simpleRule_SmsAndAll_oneProfileNotFound() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(asList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null),
                        new PriceProfileAssociationResponse("def", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "aaa", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.SMS, null, null)
                ));
        when(api.getPriceProfile(ORGANIZATION_ID, "aaa"))
                .thenReturn(failProfile(404));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, FREE_OF_CHARGE));
    }

    @Test
    public void singleCon_multipleAs_simpleRule_SmsAndAll_oneProfileFails() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(asList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null),
                        new PriceProfileAssociationResponse("def", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "aaa", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(
                        new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.SMS, null, null)
                ));
        when(api.getPriceProfile(ORGANIZATION_ID, "aaa"))
                .thenReturn(failProfile(500));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleCon_singleAs_simpleRule_failAssociationFetchServerError() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(failAssociation(500));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleCon_singleAs_simpleRule_failAssociationFetchNotFound() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(failAssociation(404));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, FREE_OF_CHARGE));
    }

    @Test
    public void singleCon_singleAs_simpleRule_failProfileFetchNotFound() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(failProfile(404));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, FREE_OF_CHARGE));
    }

    @Test
    public void singleCon_singleAs_simpleRule_failProfileFetchServerError() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(failProfile(500));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).isEmpty();
    }

    @Test
    public void singleCon_multipleAs_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(asList(
                        new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null),
                        new PriceProfileAssociationResponse("def", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null)
                )));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(singletonList(CONNECTOR_ID));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void multipleCon_singleAs_simpleRule_happyPath() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null))))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("def", 2L, NOW, ORGANIZATION_ID, "zzz", NOW, null))));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)));
        when(api.getPriceProfile(ORGANIZATION_ID, "zzz"))
                .thenReturn(completedFuture(new PriceProfileResponse("zzz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-energy-based.json"), CURRENCY, NOW, Source.ALL, null, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).containsExactlyInAnyOrder(
                new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"),
                new ConnectorPrice(new ConnectorId(2L), "10.00 SEK/kWh")
        );
    }

    @Test
    public void multipleCon_singleAs_simpleRule_failAssociationFetch() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null))))
                .thenReturn(failAssociation(500));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(completedFuture(new PriceProfileResponse("xyz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).containsExactly(new ConnectorPrice(CONNECTOR_ID, "2.00 SEK/min"));
    }

    @Test
    public void multipleCon_singleAs_simpleRule_failProfileFetch() throws Exception {
        when(api.getCurrentAssociation(anyLong()))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("abc", CONNECTOR_ID.id, NOW, ORGANIZATION_ID, "xyz", NOW, null))))
                .thenReturn(completedFuture(singletonList(new PriceProfileAssociationResponse("def", 2L, NOW, ORGANIZATION_ID, "zzz", NOW, null))));
        when(api.getPriceProfile(ORGANIZATION_ID, "xyz"))
                .thenReturn(failProfile(500));
        when(api.getPriceProfile(ORGANIZATION_ID, "zzz"))
                .thenReturn(completedFuture(new PriceProfileResponse("zzz", ORGANIZATION_ID, PROFILE_NAME, jsonFileToString("test-data/advanced-pricing/simple-time-based.json"), CURRENCY, NOW, Source.ALL, null, null)));

        List<ConnectorPrice> result = repository.priceForConnectors(asList(CONNECTOR_ID, new ConnectorId(2L)));

        assertThat(result).containsExactly(new ConnectorPrice(new ConnectorId(2L), "2.00 SEK/min"));
    }

    private CompletableFuture<List<PriceProfileAssociationResponse>> failAssociation(int code) throws Exception {
        ErrorResponse error = ErrorResponse.create().statusCode(code).build();
        HttpException e = new HttpException(Response.error(code, ResponseBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(error))));
        CompletableFuture<List<PriceProfileAssociationResponse>> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }

    private CompletableFuture<PriceProfileResponse> failProfile(int code) throws Exception {
        ErrorResponse error = ErrorResponse.create().statusCode(code).build();
        HttpException e = new HttpException(Response.error(code, ResponseBody.create(MediaType.parse("application/json"), objectMapper.writeValueAsString(error))));
        CompletableFuture<PriceProfileResponse> future = new CompletableFuture<>();
        future.completeExceptionally(e);
        return future;
    }
}
