package com.tingcore.cdc;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

public class SwaggerSpecTest extends ControllerFunctionalTest {

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