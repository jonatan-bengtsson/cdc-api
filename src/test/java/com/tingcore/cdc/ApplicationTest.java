package com.tingcore.cdc;

import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.constant.SuppressWarningConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author palmithor
 * @since 2017-08-31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles(SpringProfilesConstant.INTEGRATION_TEST)
public class ApplicationTest {


    @Autowired private HashIdService hashIdService;

    @Test
    public void contextLoads() {
        // Test that application loads
    }

    @SuppressWarnings(SuppressWarningConstant.DEPRECATION)
    @Test
    public void verifyUtcTimezone() {
        assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone("UTC"));
        Timestamp t = new Timestamp(System.currentTimeMillis());
        assertThat(t.getTimezoneOffset()).isEqualTo(0);
    }

}
