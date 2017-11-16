package com.tingcore.cdc.configuration;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import java.util.Optional;

import com.tingcore.cdc.Application;
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


  private static final Logger LOG = LoggerFactory.getLogger(ModelPropertyBuilderPlugin.class);

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
      AnnotatedField field = propertyDefinition.getField();
      if(field != null) {
        Class<?> rawType = field.getRawType();
        if (rawType.isAssignableFrom(Optional.class)) {
          required = false;
        }
      }

      AnnotatedParameter constructParameter = propertyDefinition.getConstructorParameter();
      if (constructParameter != null) {
        Class<?> rawType1 = constructParameter.getRawType();
        if (rawType1.isAssignableFrom(Optional.class)) {
          required = false;
        }
      }

      AnnotatedMethod getter = propertyDefinition.getGetter();
      if(getter != null) {
        Class<?> rawReturnType = getter.getRawReturnType();
        if (rawReturnType.isAssignableFrom(Optional.class)) {
          required = false;
        }
      }
    }

    context.getBuilder().required(required);

  }
}