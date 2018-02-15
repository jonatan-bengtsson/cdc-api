package com.tingcore.cdc.charging.mapper;

import com.tingcore.cdc.charging.model.ConnectorStatus;
import com.tingcore.charging.assets.model.*;
import com.tingcore.charging.operations.model.ChargePointStatusResponse;
import com.tingcore.charging.operations.model.ConnectorStatusResponse;
import com.tingcore.charging.operations.model.StatusBatchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConnectorStatusMapper {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectorStatusMapper.class);

    /**
     * Given a list of sites with availability rule from the asset service and a status batch response from the operations service
     * a map is created with mapping connector id with a status
     * @param statusBatchResponse a status batch response that must contain statuses for each charge point and connector part of the list of sites provided
     * @return a status map mapping connector id to respective status
     */
    public static Map<Long, ConnectorStatusResponse> getStatusMap(StatusBatchResponse statusBatchResponse) {
        Map<Long, ConnectorStatusResponse> connectorStatusMap = new HashMap<>();

        statusBatchResponse.getStatusResponses()
                .forEach(sr ->
                        sr.getConnectorStatusResponses().forEach(
                                connectorStatusResponse -> connectorStatusMap.put(connectorStatusResponse.getConnectorId(), connectorStatusResponse)
                        )
                );

        return connectorStatusMap;
    }

    public static ConnectorStatus toConnectorStatus(ConnectorStatusResponse connectorStatusResponse) {
        switch (connectorStatusResponse.getConnectorStatus()) {
            case OUT_OF_ORDER:
                return ConnectorStatus.OUT_OF_ORDER;
            case IN_USE:
                return ConnectorStatus.OCCUPIED;
            case RESERVED:
                return ConnectorStatus.RESERVED;
            case AVAILABLE:
                return ConnectorStatus.AVAILABLE;
        }

        return ConnectorStatus.NO_DATA;
    }
}
