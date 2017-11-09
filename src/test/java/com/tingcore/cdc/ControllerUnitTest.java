package com.tingcore.cdc;


import com.tingcore.cdc.configuration.JacksonConfiguration;
import com.tingcore.cdc.configuration.LocaleConfiguration;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.service.HashIdService;
import com.tingcore.cdc.service.MessageByLocaleService;
import com.tingcore.cdc.utils.MockMvcUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author palmithor
 * @since 2017-09-14
 */
@RunWith(SpringRunner.class)
@ImportAutoConfiguration({LocaleConfiguration.class, JacksonConfiguration.class})
@Import({MessageByLocaleService.class, HashIdService.class, MockMvcUtils.class})
@ActiveProfiles(SpringProfilesConstant.UNIT_TEST)
public abstract class ControllerUnitTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected MockMvcUtils mockMvcUtils;

}
