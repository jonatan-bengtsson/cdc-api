package com.tingcore.cdc.configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.commons.api.security.ClaimsHeader;
import com.tingcore.commons.constant.SuppressWarningConstant;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;

import static com.google.common.base.Predicates.or;

@Configuration
@EnableSwagger2
@SuppressWarnings(SuppressWarningConstant.SPRING_FACET_CODE_INSPECTION)
public class SwaggerConfiguration {

    private final Environment env;

    @Autowired
    public SwaggerConfiguration(final Environment env) {
        this.env = env;
    }


    @Bean
    public Docket api() {
        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .directModelSubstitute(Instant.class, Long.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, SwaggerDefaultConstant.getResponseMessages())
                .globalResponseMessage(RequestMethod.POST, SwaggerDefaultConstant.postResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, SwaggerDefaultConstant.deleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, SwaggerDefaultConstant.putResponseMessages());
        if (env.acceptsProfiles(SpringProfilesConstant.DEV, SpringProfilesConstant.TEST, SpringProfilesConstant.STAGE)) {
            // Since the service is not deployed behind API Gateway when it runs in development mode
            // This allows adding the cognito auth header in swagger in dev and test
            docket.globalOperationParameters(Lists.newArrayList(
                    new ParameterBuilder()
                            .name(ClaimsHeader.HEADER_CLAIM_USER_ID)
                            .description("The authorized user id")
                            .modelRef(new ModelRef("string"))
                            .parameterType("header")
                            .required(true)
                            .build()
            ));
        }
        return docket
                .select()
                .apis(getSelector())
                .paths(PathSelectors.any())
                .build();
    }

    private Predicate<RequestHandler> getSelector() {
        return or(
                RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)
        );

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Charge and Drive - Connect API")
                .description("")
                .version("v1")
                .build();
    }
}