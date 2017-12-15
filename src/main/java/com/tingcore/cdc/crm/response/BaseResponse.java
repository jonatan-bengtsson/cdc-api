package com.tingcore.cdc.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

import java.time.Instant;

abstract class BaseResponse {

    private final Long id;
    private final Instant created;
    private final Instant updated;

    public BaseResponse(final Long id, final Instant created, final Instant updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    public BaseResponse() {
        this.id = null;
        this.created = null;
        this.updated = null;
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
}