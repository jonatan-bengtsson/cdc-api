package com.tingcore.cdc.model.security;

import com.tingcore.cdc.model.UserReference;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Metadata {

    public final String clientAddress; //TODO: Specific type for this
    public final UserReference userReference;
    public final Optional<String> originalRequestedHostname; //TODO: Specific type for this
    public final Optional<String> originalClientAddress; //TODO: Specific type for this
    public final Optional<String> email; //TODO: Specific type for this

    public Metadata(final String clientAddress,
                    final UserReference userReference,
                    final String originalRequestedHostname,
                    final String originalClientAddress,
                    final String email) {
        this(clientAddress, userReference, ofNullable(originalRequestedHostname), ofNullable(originalClientAddress), ofNullable(email));
    }

    private Metadata(final String clientAddress,
                     final UserReference userReference,
                     final Optional<String> originalRequestedHostname,
                     final Optional<String> originalClientAddress,
                     final Optional<String> email) {
        this.clientAddress = notBlank(clientAddress);
        this.userReference = notNull(userReference);
        this.originalRequestedHostname = notNull(originalRequestedHostname);
        this.originalClientAddress = notNull(originalClientAddress);
        this.email = notNull(email);
    }
}
