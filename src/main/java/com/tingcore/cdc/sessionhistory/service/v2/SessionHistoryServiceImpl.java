package com.tingcore.cdc.sessionhistory.service.v2;

import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.payments.history.v2.ApiChargeHistoryAdapter;
import com.tingcore.cdc.sessionhistory.repository.v2.SessionHistoryRepository;
import com.tingcore.cdc.sessionhistory.service.SessionHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionHistoryServiceImpl implements SessionHistoryService {
    private final SessionHistoryRepository sessionHistoryRepository;

    public SessionHistoryServiceImpl(final SessionHistoryRepository sessionHistoryRepository) {
        this.sessionHistoryRepository = sessionHistoryRepository;
    }

    @Override
    public List<ApiChargeHistory> getSessionHistory(final Long chargingKeyId, final Long from, final Long to) {
        return sessionHistoryRepository
                .getSession(chargingKeyId, from, to)
                .stream()
                .map(ApiChargeHistoryAdapter::new)
                .collect(Collectors.toList());
    }
}
