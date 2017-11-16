package com.tingcore.cdc.configuration;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.tingcore.cdc.constant.SuppressWarningConstant;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.commons.rest.SwaggerDefaultConstant;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@SuppressWarnings(SuppressWarningConstant.SPRING_FACET_CODE_INSPECTION)
public class SwaggerConfiguration {

    @Autowired
    private TypeResolver typeResolver;

    private static final ResponseMessage BAD_REQUEST_RESPONSE_MESSAGE = new ResponseMessageBuilder()
            .code(SwaggerDefaultConstant.HTTP_STATUS_BAD_REQUEST)
            .message(SwaggerDefaultConstant.MESSAGE_BAD_REQUEST)
            .responseModel(new ModelRef(ErrorResponse.class.getSimpleName())).build();

    private static final ResponseMessage NOT_FOUND_RESPONSE_MESSAGE = new ResponseMessageBuilder()
            .code(SwaggerDefaultConstant.HTTP_STATUS_NOT_FOUND)
            .message(SwaggerDefaultConstant.MESSAGE_NOT_FOUND)
            .responseModel(new ModelRef(ErrorResponse.class.getSimpleName())).build();

    private static final ResponseMessage INTERNAL_SERVER_RESPONSE_MESSAGE = new ResponseMessageBuilder()
            .code(SwaggerDefaultConstant.HTTP_STATUS_INTERNAL_SERVER_ERROR)
            .message(SwaggerDefaultConstant.MESSAGE_INTERNAL_SERVER_ERROR)
            .responseModel(new ModelRef(ErrorResponse.class.getSimpleName())).build();




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
                .globalResponseMessage(RequestMethod.GET, getResponseMessages())
                .globalResponseMessage(RequestMethod.POST, postResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, deleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, putResponseMessages())
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


    private List<ResponseMessage> putResponseMessages() {
        return newArrayList(
                BAD_REQUEST_RESPONSE_MESSAGE,
                NOT_FOUND_RESPONSE_MESSAGE,
                INTERNAL_SERVER_RESPONSE_MESSAGE
        );
    }

    private List<ResponseMessage> deleteResponseMessages() {
        return newArrayList(
                BAD_REQUEST_RESPONSE_MESSAGE,
                NOT_FOUND_RESPONSE_MESSAGE,
                INTERNAL_SERVER_RESPONSE_MESSAGE
        );
    }

    private List<ResponseMessage> postResponseMessages() {
        return newArrayList(
                BAD_REQUEST_RESPONSE_MESSAGE,
                INTERNAL_SERVER_RESPONSE_MESSAGE
        );
    }

    private List<ResponseMessage> getResponseMessages() {
        return newArrayList(
                BAD_REQUEST_RESPONSE_MESSAGE,
                NOT_FOUND_RESPONSE_MESSAGE,
                INTERNAL_SERVER_RESPONSE_MESSAGE
        );
    }
}