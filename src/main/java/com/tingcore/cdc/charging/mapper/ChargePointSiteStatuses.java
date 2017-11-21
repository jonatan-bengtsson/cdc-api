package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.ChargeSiteStatus;

public class ChargePointSiteStatuses {
    private ChargeSiteStatus quickStatus;
    private ChargeSiteStatus status;

    public ChargePointSiteStatuses() {}

    public ChargePointSiteStatuses(ChargeSiteStatus quickStatus, ChargeSiteStatus status) {
        this.quickStatus = quickStatus;
        this.status = status;
    }

    public ChargeSiteStatus getQuickStatus() {
        return quickStatus;
    }

    public ChargeSiteStatus getStatus() {
        return status;
    }

    public void setQuickStatus(ChargeSiteStatus quickStatus) {
        this.quickStatus = quickStatus;
    }

    public void setStatus(ChargeSiteStatus status) {
        this.status = status;
    }


}
