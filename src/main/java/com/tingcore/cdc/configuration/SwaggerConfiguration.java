package com.tingcore.cdc.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.tingcore.cdc.constant.SuppressWarningConstant;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.Optional;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@SuppressWarnings(SuppressWarningConstant.SPRING_FACET_CODE_INSPECTION)
public class SwaggerConfiguration {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .directModelSubstitute(Instant.class, Long.class)
                .alternateTypeRules(
                        newRule(
                                typeResolver.resolve(Optional.class, WildcardType.class),
                                typeResolver.resolve(WildcardType.class)
                        )
                )
                .genericModelSubstitutes(Optional.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, SwaggerDefaultConstant.getResponseMessages())
                .globalResponseMessage(RequestMethod.POST, SwaggerDefaultConstant.postResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, SwaggerDefaultConstant.deleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, SwaggerDefaultConstant.putResponseMessages())
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