package com.tingcore.cdc.sessionhistory.service;

import com.tingcore.cdc.payments.history.ApiChargeHistory;

import java.util.List;

public interface SessionHistoryService {
    List<ApiChargeHistory> getSessionHistory(final Long chargingKeyId, final Long from, final Long to);
}
