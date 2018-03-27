package com.tingcore.cdc.sessionhistory.service;

import com.tingcore.cdc.sessionhistory.repository.SessionHistoryRepository;
import com.tingcore.payments.cpo.model.ApiChargeHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionHistoryService {

    private final SessionHistoryRepository sessionHistoryRepository;

    public SessionHistoryService(final SessionHistoryRepository sessionHistoryRepository) {
        this.sessionHistoryRepository = sessionHistoryRepository;
    }

    public List<ApiChargeHistory> getSessionHistory(final Long id, final Long from, final Long to) {
        return sessionHistoryRepository.getSessionHistory(id, from, to);
    }
}
