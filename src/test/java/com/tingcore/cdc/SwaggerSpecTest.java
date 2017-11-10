package com.tingcore.cdc;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("dev")
public class SwaggerSpecTest {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SwaggerSpecTest.class);

    private static final String API_URI = "/v2/api-docs";

    @Autowired
    private WebApplicationContext wac;

    private String swaggerOutputPath = "build/swagger/";

    private MockMvc mockMvc;

    @Before
    public void setup() throws IOException {

        Assume.assumeTrue(swaggerOutputPath != null
                && !swaggerOutputPath.isEmpty());

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        LOGGER.info("The swagger output is {}", swaggerOutputPath);
    }

    @Test
    public void createSwaggerSpec() throws Exception {

        ResultHandler rh = r -> {
            String swaggerJSONAsString = r.getResponse().getContentAsString();
            File swaggerDir = new File(swaggerOutputPath);
            swaggerDir.mkdirs();
            assertThat(swaggerDir.exists()).isTrue();
            File swaggerFile = new File(swaggerDir, "swagger.json");

            Files.write(Paths.get(swaggerFile.getAbsolutePath()),
                    swaggerJSONAsString.getBytes("UTF-8"));
            assertThat(swaggerFile.exists()).isTrue();
        };

        mockMvc.perform(get(API_URI).accept(MediaType.APPLICATION_JSON)).andDo(rh);

    }
}