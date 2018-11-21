package com.tingcore.cdc.sessionhistory.service.v1;

import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.payments.history.v1.ApiChargeHistoryAdapter;
import com.tingcore.cdc.sessionhistory.repository.v1.SessionHistoryRepository;
import com.tingcore.cdc.sessionhistory.service.SessionHistoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tingcore.cdc.constant.SpringProfilesConstant.SESSION_HISTORY_V2;

@Service
@Profile("!" + SESSION_HISTORY_V2)
public class SessionHistoryServiceImpl implements SessionHistoryService {

    private final SessionHistoryRepository sessionHistoryRepository;

    public SessionHistoryServiceImpl(final SessionHistoryRepository sessionHistoryRepository) {
        this.sessionHistoryRepository = sessionHistoryRepository;
    }

    @Override
    public List<ApiChargeHistory> getSessionHistory(final Long chargingKeyId, final Long from, final Long to) {
        return sessionHistoryRepository
                .getSessionHistory(chargingKeyId, from, to)
                .stream()
                .filter(Objects::nonNull)
                .map(ApiChargeHistoryAdapter::new)
                .collect(Collectors.toList());
    }
}
