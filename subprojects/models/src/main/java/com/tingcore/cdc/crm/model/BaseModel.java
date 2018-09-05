package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;
import java.util.Objects;

abstract class BaseModel {

    private final Long id;
    private final Instant created;
    private final Instant updated;

    protected BaseModel(final Long id, final Instant created, final Instant updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    protected BaseModel() {
        id = null;
        created = null;
        updated = null;
    }

    @JsonProperty(FieldConstant.ID)
    @ApiModelProperty(value = "The model id", example = "1", required = true)
    public Long getId() {
        return id;
    }

    @JsonProperty(FieldConstant.CREATED)
    @ApiModelProperty(value = "The date that this entity was created, formatted as epoch (ms)", example = "1509006978565", required = true)
    public Instant getCreated() {
        return created;
    }

    @JsonProperty(FieldConstant.UPDATED)
    @ApiModelProperty(value = "The date that this entity was last modified, formatted as epoch (ms)", example = "1509006978565", required = true)
    public Instant getUpdated() {
        return updated;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BaseModel baseModel = (BaseModel) o;
        return Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}