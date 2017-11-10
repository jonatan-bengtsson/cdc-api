package com.tingcore.cdc.security;

import com.tingcore.library.eventprocessing.AuditEvent;

import static org.apache.commons.lang3.Validate.notNull;

public class AuthorizationFailedEvent implements AuditEvent {
    public final String cause;

    public AuthorizationFailedEvent(final String cause) {
        this.cause = notNull(cause);
    }
}
