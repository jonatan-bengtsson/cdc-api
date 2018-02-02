package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.constant.SwaggerDocConstants;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public abstract class BaseAttributeModel {
    Long id;

    public BaseAttributeModel() {
    }

    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(position = 1, value = "The attribute value id. If the id is included it means that the value" +
            "should be updated, if not it should be created", example = SwaggerDocConstants.EXAMPLE_ID)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract <T extends BaseAttributeModel> T copyWithoutId ();

}
