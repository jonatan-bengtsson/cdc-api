package com.tingcore.cdc.sessionhistory.repository.v2;

import com.tingcore.cdc.ElasticSearchTest;
import com.tingcore.commons.core.utils.JsonUtils;
import com.tingcore.payments.sessionstasher.models.v1.Session;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionHistoryRepositoryTest extends ElasticSearchTest {
    private static boolean isTestDataLoaded = false;
    private final SessionHistoryRepository repository = new SessionHistoryRepository(JsonUtils.getObjectMapper(), client);

    @Before
    public void setup() throws Exception {
        if (isTestDataLoaded) {
            return;
        }
        createIndex("sessions", "{\"_doc\": {}}", "{}");
        indexData("sessions", testData());

        Thread.sleep(1500);
        isTestDataLoaded = true;
    }

    @Test
    public void testNoHits() {
        final List<Session> sessions = repository.getSession(1337L, 12414L, 1111114L);

        assertThat(sessions).isEmpty();
    }

    @Test
    public void testHits() {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        final long from = formatter.parse("2018-12-12T10:31:00.343Z", Instant::from).toEpochMilli();
        final long to = formatter.parse("2018-12-12T10:36:30.343Z", Instant::from).toEpochMilli();

        final List<Session> sessions = repository.getSession(1001765L, from, to);

        assertThat(sessions.size()).isEqualTo(3);
        assertThat(sessions.get(0).getId()).isEqualTo(5);
        assertThat(sessions.get(1).getId()).isEqualTo(4);
        assertThat(sessions.get(2).getId()).isEqualTo(2);
    }

    private static Map<Object, String> testData() {
        Map<Object, String> data = new HashMap<>();
        data.put(11143274498234L, "{\"id\": 1,\"userId\":453466,  \"user\": {\"keyIdentifier\": \"193757057kjh\", \"id\": 453466, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":845083,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":14, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":{\"id\":5,\"name\":\"yolo\"},\"operator\":null}},\"startedAt\":\"2018-12-12T10:30:30.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":1001765,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\": { \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:40:30.343Z\"}");
        data.put(6347808780700L, "{\"id\": 2,\"userId\":8708457, \"user\": {\"keyIdentifier\": null, \"id\": 8708457, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":1123, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":null,\"operator\":null}},\"startedAt\":\"2018-12-12T10:31:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":1001765,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:41:30.343Z\"}");
        data.put(98234926583527L, "{\"id\": 3,\"userId\":4365776, \"user\": {\"keyIdentifier\": null, \"id\": 4365776, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":24546, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":null,\"operator\":null}},\"startedAt\":\"2018-12-12T10:32:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":3657,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:42:30.343Z\"}");
        data.put(107474923469236L, "{\"id\": 4,\"userId\":1960467, \"user\": {\"keyIdentifier\": null, \"id\": 1960467, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":154345, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":null,\"operator\":null}},\"startedAt\":\"2018-12-12T10:33:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":1001765,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:43:30.343Z\"}");
        data.put(104730743827408L, "{\"id\": 5,\"userId\":1960467, \"user\": {\"keyIdentifier\": null, \"id\": 1960467, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":45345, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":null,\"operator\":null}},\"startedAt\":\"2018-12-12T10:34:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":1001765,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:50:30.343Z\"}");
        data.put(177347834708248L, "{\"id\": 6,\"userId\":1960467, \"user\": {\"keyIdentifier\": null, \"id\": 1960467, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":4545, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":null,\"operator\":null}},\"startedAt\":\"2018-12-12T10:35:00.343Z\",\"identity\":{\"type\":\"SMS Customer\",\"hashedPhoneNumber\":\"hashed\"},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:45:30.343Z\"}");
        data.put(183034930984398L, "{\"id\": 7,\"userId\":1960467, \"user\": {\"keyIdentifier\": null, \"id\": 1960467, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":345, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":{\"id\":7435,\"name\":\"yolo\"},\"operator\":null}},\"startedAt\":\"2018-12-12T10:36:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":646464,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:46:30.343Z\"}");
        data.put(984096856793446L, "{\"id\": 8,\"userId\":1960467, \"user\": {\"keyIdentifier\": null, \"id\": 1960467, \"email\": \"email\" },\"connector\":{\"id\":15254,\"label\":null,\"type\":null,\"chargePoint\":{\"id\":9581,\"name\":\"CD_TEST_NO_1Æ Ø Å\",\"modelId\":null,\"assetLabel\":null,\"chargePointSite\":{\"id\":345, \"name\": \"yolo-1\", \"address\": { \"countryIsoCode\": \"se\" }, \"geoCoordinate\": {\"lon\": 10.751698,\"lat\": 59.923385}},\"owner\":{\"id\":9054,\"name\":\"yolo\"},\"operator\":null}},\"startedAt\":\"2018-12-12T10:37:00.343Z\",\"identity\":{\"type\":\"Internal Customer\",\"chargingKey\":{\"id\":1001765,\"typeName\":null}},\"status\":\"CLEARED\",\"payment\":{\"balance\":{ \"amountMinorUnitsIncl\": 0, \"currency\": \"SEK\"},\"transactions\":[{\"amount\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"payeeAccountId\":null,\"payerCustomerId\":null,\"paymentMethod\":\"INVOICE\",\"paymentProviderTransactionId\":\"8316609342295436\",\"details\":\"Payment taken using invoice\",\"time\":\"2018-12-12T10:32:03.056Z\",\"payerAccountId\":\"FAD35F1E-4FB9-46C8-987B-2A9F0E7F96CB\",\"paymentOutcome\":\"CHARGE\"}],\"price\":{\"vat\":\"25\",\"currency\":\"NOK\",\"amountMinorUnitsIncl\":450,\"amountMinorUnitsExcl\":360},\"receiptId\":8044544201018331},\"stoppedAt\":\"2018-12-12T10:47:30.343Z\"}");
        return data;
    }
}
