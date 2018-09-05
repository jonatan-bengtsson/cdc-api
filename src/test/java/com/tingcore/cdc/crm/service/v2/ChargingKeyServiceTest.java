package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.ChargingKeyRepository;
import com.tingcore.cdc.crm.request.UpdateChargingKeyAppRequest;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.cdc.exception.EntityNotFoundException;
import com.tingcore.cdc.utils.CommonDataUtils;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v2.request.ChargingKeyActivationRequest;
import com.tingcore.users.model.v2.response.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
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

    @Test
    public void update() {
        final Long authorizedUserId = CommonDataUtils.getNextId();
        final Long chargingKeyId = CommonDataUtils.getNextId();

        final UpdateChargingKeyAppRequest appRequest = new UpdateChargingKeyAppRequest("new name");

        final PrivateCustomer owner = PrivateCustomer.createBuilder().id(authorizedUserId).build();
        final ChargingKey chargingKey = ChargingKey.createBuilder().id(chargingKeyId).name("old name").owner(owner).build();
        given(repository.getByChargingKeyId(anyLong(),anyLong())).willReturn(new ApiResponse<>(chargingKey));

        final ChargingKey mockResponse = ChargingKey.createBuilder().id(chargingKey.getId()).name(appRequest.getChargingKeyName()).build();

        given(repository.updateChargingKey(anyLong(), anyLong(), any())).willReturn(new ApiResponse<>(mockResponse));

        final ChargingKey response = unitUnderTest.updateChargingKey(authorizedUserId, chargingKeyId, appRequest);

        assertThat(response.getName()).isEqualTo(appRequest.getChargingKeyName());
    }

    @Test
    public void updateFailWrongOwnerId() {
        final Long ownerId = CommonDataUtils.getNextId();
        final PrivateCustomer owner = PrivateCustomer.createBuilder().id(ownerId).build();
        final ChargingKey chargingKey = ChargingKey.createBuilder().id(CommonDataUtils.getNextId()).owner(owner).build();

        given(repository.getByChargingKeyId(anyLong(),anyLong())).willReturn(new ApiResponse<>(chargingKey));

        final UpdateChargingKeyAppRequest appRequest = new UpdateChargingKeyAppRequest("new name");

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> unitUnderTest.updateChargingKey(0L, CommonDataUtils.getNextId(), appRequest));
    }
}
