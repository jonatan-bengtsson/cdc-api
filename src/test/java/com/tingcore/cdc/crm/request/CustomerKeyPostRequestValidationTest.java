package com.tingcore.cdc.crm.request;

import com.tingcore.cdc.crm.constant.FieldConstant;
import com.tingcore.cdc.crm.utils.CustomerKeyDataUtils;
import com.tingcore.cdc.utils.ValidationTest;
import com.tingcore.commons.core.constant.SuppressWarningConstant;
import com.tingcore.commons.core.utils.StreamUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.validation.ConstraintViolation;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author palmithor
 * @since 2018-01-05
 */
@SuppressWarnings(SuppressWarningConstant.CONSTANT_CONDITIONS)
public class CustomerKeyPostRequestValidationTest extends ValidationTest {


    @Test
    public void failNameNull() {
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(
                CustomerKeyDataUtils.randomRequestAllValid().name(null).build()
        );
        assertThat(constraintViolation.getInvalidValue()).isNull();
        assertThat(constraintViolation.getMessage()).isEqualTo("must not be null");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.NAME);
    }

    @Test
    public void failNameEmpty() {
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(
                CustomerKeyDataUtils.randomRequestAllValid().name(StringUtils.EMPTY).build()
        );
        assertThat(constraintViolation.getInvalidValue()).isEqualTo(StringUtils.EMPTY);
        assertThat(constraintViolation.getMessage()).isEqualTo("size must be between 1 and 40");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.NAME);
    }


    @Test
    public void failNameTooLong() {
        final CustomerKeyPostRequest request = CustomerKeyDataUtils.randomRequestAllValid()
                .name(StringUtils.leftPad(StringUtils.EMPTY, 41, "*")).build();
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(request);
        assertThat(constraintViolation.getInvalidValue()).isEqualTo(request.getName());
        assertThat(constraintViolation.getMessage()).isEqualTo("size must be between 1 and 40");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.NAME);
    }

    @Test
    public void failKeyIdentifierNull() {
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(
                CustomerKeyDataUtils.randomRequestAllValid().keyIdentifier(null).build()
        );
        assertThat(constraintViolation.getInvalidValue()).isNull();
        assertThat(constraintViolation.getMessage()).isEqualTo("must not be null");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.KEY_IDENTIFIER);
    }

    @Test
    public void failKeyIdentifierEmpty() {
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(
                CustomerKeyDataUtils.randomRequestAllValid().keyIdentifier(StringUtils.EMPTY).build()
        );
        assertThat(constraintViolation.getInvalidValue()).isEqualTo(StringUtils.EMPTY);
        assertThat(constraintViolation.getMessage()).isEqualTo("size must be between 1 and 20");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.KEY_IDENTIFIER);
    }


    @Test
    public void failKeyIdentifierTooLong() {
        final CustomerKeyPostRequest request = CustomerKeyDataUtils.randomRequestAllValid()
                .keyIdentifier(StringUtils.leftPad(StringUtils.EMPTY, 21, "*")).build();
        ConstraintViolation<CustomerKeyPostRequest> constraintViolation = validateAndGetSingleViolation(request);
        assertThat(constraintViolation.getInvalidValue()).isEqualTo(request.getKeyIdentifier());
        assertThat(constraintViolation.getMessage()).isEqualTo("size must be between 1 and 20");
        assertThat(StreamUtils.asStream(constraintViolation.getPropertyPath().iterator()).reduce((first, second) -> second).get().toString()).isEqualTo(FieldConstant.KEY_IDENTIFIER);
    }

}