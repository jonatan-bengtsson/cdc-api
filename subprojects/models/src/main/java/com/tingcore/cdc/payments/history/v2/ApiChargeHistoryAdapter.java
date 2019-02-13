package com.tingcore.cdc.payments.history.v2;

import com.tingcore.cdc.payments.history.ApiAmount;
import com.tingcore.cdc.payments.history.ApiChargeHistory;
import com.tingcore.cdc.payments.history.ApiSessionEnergy;
import com.tingcore.payments.sessionstasher.models.v1.*;

import java.time.Instant;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notNull;

public class ApiChargeHistoryAdapter implements ApiChargeHistory {
    private enum BalanceStatus {
        NOT_PAID, PAID, REFUND;

        public static BalanceStatus fromBalance(final Balance balance) {
            final int signum = Long.signum(balance.getAmountMinorUnitsIncl());
            return signum == 0 ? PAID : (signum < 0 ? NOT_PAID : REFUND);
        }
    }

    private final Session session;

    public ApiChargeHistoryAdapter(final Session session) {
        this.session = notNull(session);
    }

    @Override
    public Long getSessionId() {
        return session.getId();
    }

    @Override
    public ApiAmount getPrice() {
        return ofNullable(session.getPayment())
                .map(Payment::getPrice)
                .map(ApiAmountAdapter::new)
                .orElse(null);
    }

    @Override
    public Long getConnectorId() {
        return ofNullable(session.getConnector())
                .map(Connector::getId)
                .orElse(null);
    }

    @Override
    public Long getStartTime() {
        return ofNullable(session.getStartedAt())
                .map(Instant::toEpochMilli)
                .orElse(null);
    }

    @Override
    public String getSite() {
        return ofNullable(session.getConnector())
                .map(Connector::getChargePoint)
                .map(ChargePoint::getChargePointSite)
                .map(ChargePointSite::getName)
                .orElse(null);
    }

    @Override
    public String getBalanceStatus() {
        return ofNullable(session.getPayment())
                .map(Payment::getBalance)
                .map((balance) -> BalanceStatus.fromBalance(balance).name())
                .orElse(BalanceStatus.NOT_PAID.name());
    }

    @Override
    public String getSessionStatus() {
        return session.getStatus()
                .name();
    }

    @Override
    public Long getOrganizationId() {
        return ofNullable(session.getConnector())
                .map(Connector::getChargePoint)
                .map(ChargePoint::getOwner)
                .map(Organization::getId)
                .orElse(null);
    }

    @Override
    public ApiSessionEnergy getEnergy() {
        return ofNullable(session.getEnergy())
                .map(ApiSessionEnergyAdapter::new)
                .orElse(null);
    }
}
