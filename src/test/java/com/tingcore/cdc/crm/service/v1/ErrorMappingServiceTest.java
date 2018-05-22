package com.tingcore.cdc.crm.service.v1;

import com.tingcore.cdc.crm.service.ErrorMappingService;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import org.hashids.Hashids;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author palmithor
 * @since 2018-02-06
 */
public class ErrorMappingServiceTest {

    private ErrorMappingService service;
    private HashIdService hashIdService;

    @Before
    public void setUp() {
        this.hashIdService = new HashIdService(new Hashids());
        this.service = new ErrorMappingService(hashIdService);
    }

    @Test
    public void shouldHashIdentityId() {
        final String message = "User defined by '1' does not exist";
        final ErrorResponse updatedErrorResponse = service.prepareErrorResponse(ErrorResponse.notFound().addDetail(message).build());
        assertThat(updatedErrorResponse.getDetails()).hasSize(1);
        assertThat(updatedErrorResponse.getDetails().get(0)).isEqualTo(String.format("User defined by '%s' does not exist", hashIdService.encode(1L)));
    }

    @Test
    public void shouldNotHashEntityField() {
        final String message = "CustomerKey with field 'keyIdentifier' equal to '123' does not exist";
        final ErrorResponse updatedErrorResponse = service.prepareErrorResponse(ErrorResponse.notFound().addDetail(message).build());
        assertThat(updatedErrorResponse.getDetails()).hasSize(1);
        assertThat(updatedErrorResponse.getDetails().get(0)).isEqualTo(message);
    }

    @Test
    public void shouldNotHashEntityNotFound() {
        final String message = "User not found";
        final ErrorResponse updatedErrorResponse = service.prepareErrorResponse(ErrorResponse.notFound().addDetail(message).build());
        assertThat(updatedErrorResponse.getDetails()).hasSize(1);
        assertThat(updatedErrorResponse.getDetails().get(0)).isEqualTo(message);
    }

    @Test
    public void shouldNotHashOther() {
        final String message = "Hello world";
        final ErrorResponse updatedErrorResponse = service.prepareErrorResponse(ErrorResponse.notFound().addDetail(message).build());
        assertThat(updatedErrorResponse.getDetails()).hasSize(1);
        assertThat(updatedErrorResponse.getDetails().get(0)).isEqualTo(message);
    }
}