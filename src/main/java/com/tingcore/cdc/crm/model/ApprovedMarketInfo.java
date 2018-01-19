package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tingcore.cdc.crm.constant.FieldConstant;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-14.
 */
public class ApprovedMarketInfo extends BaseAttributeModel {
    private String value;

    public ApprovedMarketInfo(Long valueId, String value) {
        this.id = valueId;
        this.value = value;
    }

    public ApprovedMarketInfo() {
    }

    @JsonProperty(FieldConstant.MARKET_INFO_ID)
    @ApiModelProperty(value = "The id of the approved market info")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public ApprovedMarketInfo copyWithoutId () {
        return new ApprovedMarketInfo(null, this.value);
    }
}
