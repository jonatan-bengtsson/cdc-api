package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public abstract class BaseAttributeModel {
    Long id;

    public BaseAttributeModel() {
    }

    @JsonProperty(FieldConstant.ID)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
