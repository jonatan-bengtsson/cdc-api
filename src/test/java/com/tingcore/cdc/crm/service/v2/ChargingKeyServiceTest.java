package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.ChargingKeyRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.v2.request.ChargingKeyActivationRequest;
import com.tingcore.users.model.v2.response.ChargingKey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ChargingKeyServiceTest {

    @MockBean private ChargingKeyRepository repository;

    private ChargingKeyService unitUnderTest;

    private static final String keyIdentifier = "key-identifier";
    private static final Long userId = 1L;

    @Before
    public void setUp() {
        unitUnderTest = new ChargingKeyService(repository);
    }

    @Test
    public void activate() {


        ArgumentCaptor<ChargingKeyActivationRequest> argument = ArgumentCaptor.forClass(ChargingKeyActivationRequest.class);

        when(repository.activate(anyLong(), argument.capture()))
                .thenReturn(new ApiResponse<>(ChargingKey.createBuilder().keyIdentifier(keyIdentifier).build()));

        unitUnderTest.activate(userId, keyIdentifier);

        assertThat(argument.getValue().getKeyIdentifier()).isEqualTo(keyIdentifier);
        assertThat(argument.getValue().getUserId()).isEqualTo(userId);
    }

    @Test
    public void failCreateOrder() {
        when(repository.activate(anyLong(), any(ChargingKeyActivationRequest.class)))
                .thenReturn(new ApiResponse<>(ErrorResponse.badRequest().build()));

        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> unitUnderTest.activate(userId, keyIdentifier))
                .withNoCause();
    }
}
