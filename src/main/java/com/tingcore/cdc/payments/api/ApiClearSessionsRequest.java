package com.tingcore.cdc.payments.api;

import io.swagger.annotations.ApiModel;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@ApiModel
public class ApiClearSessionsRequest {
    final List<String> sessionIds;

    public ApiClearSessionsRequest(final List<String> sessionIds) {
        this.sessionIds = notNull(sessionIds);
    }

    public List<String> getSessionIds() {
        return sessionIds;
    }
}
