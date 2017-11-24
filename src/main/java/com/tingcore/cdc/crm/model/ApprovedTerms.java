package com.tingcore.cdc.crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author moa.mackegard
 * @since 2017-11-21.
 */
public class ApprovedTerms extends BaseAttribute {

    private String termId;

    public ApprovedTerms(String termId) {
        this.termId = termId;
    }

    @JsonProperty("termId")
    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }
}
