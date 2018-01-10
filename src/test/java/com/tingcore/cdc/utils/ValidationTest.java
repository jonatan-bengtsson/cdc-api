package com.tingcore.cdc.utils;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author palmithor
 * @since 2017-07-20.
 */
@RunWith(SpringRunner.class)
@Import({ValidationAutoConfiguration.class})
public abstract class ValidationTest {

    private Validator validator;

    @Autowired private LocalValidatorFactoryBean validatorFactoryBean;

    @Before
    public void setUp() {
        this.validator = validatorFactoryBean.getValidator();
    }

    protected <T> ConstraintViolation<T> validateAndGetSingleViolation(final T request) {
        Set<ConstraintViolation<T>> constraints = validator.validate(request);
        assertThat(constraints).hasSize(1);
        return constraints.iterator().next();
    }


}
