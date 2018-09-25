package com.tingcore.cdc.crm.service.v2;

import com.tingcore.cdc.crm.repository.v2.CustomerRepository;
import com.tingcore.cdc.crm.service.UsersApiException;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.repository.ApiResponse;
import com.tingcore.users.model.v2.request.PrivateCustomerUpdateRequest;
import com.tingcore.users.model.v2.response.PrivateCustomer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @MockBean
    private CustomerRepository repository;

    private CustomerService underUnitTest;
    private static final Long userId = 1L;

    @Before
    public void setUp(){
        underUnitTest = new CustomerService(repository);
    }

    @Test
    public void getById() {
        given(repository.getSelf(anyLong()))
                .willReturn(new ApiResponse<>(PrivateCustomer.createBuilder().firstName("customer").build()));
        assertThatCode(() -> underUnitTest.findById(userId)).doesNotThrowAnyException();
    }

    @Test
    public void failGetById() {
        given(repository.getSelf(anyLong())).willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> underUnitTest.findById(userId));
    }

    @Test
    public void updateCustomer() {
        given(repository.update(anyLong(), any()))
                .willReturn(new ApiResponse<>(PrivateCustomer.createBuilder().firstName("customer").build()));
        PrivateCustomerUpdateRequest request = PrivateCustomerUpdateRequest.createBuilder().version(2L).build();
        assertThatCode(() -> underUnitTest.update(userId, request))
                .doesNotThrowAnyException();
    }

    @Test
    public void failUpdateCustomer() {
        given(repository.update(anyLong(), any()))
                .willReturn(new ApiResponse<>(ErrorResponse.notFound().build()));
        PrivateCustomerUpdateRequest request = PrivateCustomerUpdateRequest.createBuilder().version(2L).build();
        assertThatExceptionOfType(UsersApiException.class)
                .isThrownBy(() -> underUnitTest.update(userId, request));
    }
}
