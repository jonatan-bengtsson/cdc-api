package com.tingcore.cdc.service;

import io.swagger.models.Model;
import io.swagger.models.Swagger;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.LongProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Documentation;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author palmithor
 * @since 2017-10-30
 */
@Component
@Primary // required so this class is injected where ServiceModelToSwagger2Mapper is autowired
public class CustomServiceModelToSwagger2Mapper extends ServiceModelToSwagger2MapperImpl {

    private static final Logger LOG = LoggerFactory.getLogger(CustomServiceModelToSwagger2Mapper.class);

    private final HashIdService hashIdService;
    private final long exampleId;

    public CustomServiceModelToSwagger2Mapper(final HashIdService hashIdService) {
        this.hashIdService = hashIdService;
        Random r = new Random();
        final long lowerLimit = 1000;
        final long upperLimit = 2000;
        this.exampleId = lowerLimit + ((long) (r.nextDouble() * (upperLimit - lowerLimit)));
    }

    @Override
    public Swagger mapDocumentation(final Documentation from) {
        final Swagger swagger = super.mapDocumentation(from);
        final Map<String, Model> updatedDefinitions = new HashMap<>(swagger.getDefinitions().size());

        swagger.getDefinitions().forEach((definitionKey, definitionModel) -> {
            final Map<String, Property> updatedProperties = new HashMap<>();
            if (definitionModel.getProperties() != null) {
                definitionModel.getProperties().forEach((propertiesKey, property) -> {
                    if ((property instanceof LongProperty) && HashIdService.isIdKey(property.getName())) {
                        final StringProperty stringProperty = convertToStringProperty(property);
                        updatedProperties.put(propertiesKey, stringProperty);
                    } else if ( isLongList(property) && HashIdService.isIdKey(property.getName())) {
                        final ArrayProperty stringListProperty = convertToStringListProperty(property);
                        updatedProperties.put(propertiesKey, stringListProperty);
                    } else {
                        updatedProperties.put(propertiesKey, property);
                    }
                });
                definitionModel.setProperties(updatedProperties);
                updatedDefinitions.put(definitionKey, definitionModel);
            }
        });
        swagger.setDefinitions(updatedDefinitions);
        return swagger;
    }

    private ArrayProperty convertToStringListProperty(Property property) {
        ArrayProperty arrayProp = (ArrayProperty) property;
        arrayProp.setItems(convertToStringProperty(arrayProp.getItems()));
        return arrayProp;
    }

    private boolean isLongList(Property property) {
        if(property instanceof ArrayProperty) {
            ArrayProperty arrayProp = (ArrayProperty) property;
            return arrayProp.getItems() instanceof LongProperty;
        }
        return false;
    }

    private StringProperty convertToStringProperty(final Property property) {
        final StringProperty stringProperty = new StringProperty(StringProperty.TYPE);
        stringProperty.example(hashIdService.encode(exampleId));
        stringProperty.setRequired(property.getRequired());
        stringProperty.setMaxLength(HashIdService.HASH_LENGTH);
        stringProperty.setMinLength(HashIdService.HASH_LENGTH);
        stringProperty.setDescription(property.getDescription());
        stringProperty.setTitle(property.getTitle());
        stringProperty.setPosition(property.getPosition());
        return stringProperty;
    }
}
