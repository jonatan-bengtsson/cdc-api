package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.util.Optional;
import org.apache.logging.log4j.core.config.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;


@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER+1)
public class OptionalModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }

  @Override
  public void apply(ModelPropertyContext context) {
    com.google.common.base.Optional<BeanPropertyDefinition> beanPropertyDefinition = context.getBeanPropertyDefinition();
    boolean required = true;
    if (beanPropertyDefinition.isPresent()) {
      BeanPropertyDefinition propertyDefinition = beanPropertyDefinition.get();
      Class<?> rawType = propertyDefinition.getField().getRawType();

      AnnotatedParameter constructParameter = propertyDefinition.getConstructorParameter();
      if (constructParameter != null) {
        Class<?> rawType1 = constructParameter.getRawType();
        if (rawType1.isAssignableFrom(Optional.class)) {
          required = false;
        }
      }

      Class<?> rawReturnType = propertyDefinition.getGetter().getRawReturnType();
      if (rawReturnType.isAssignableFrom(Optional.class)) {
        required = false;
      }

      if (rawType.isAssignableFrom(Optional.class)) {
        required = false;
      }
    }

    context.getBuilder().required(required);

  }
}