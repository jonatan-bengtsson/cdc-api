package com.tingcore.cdc.model.security;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Metadata {

    public final String clientAddress;
    public final Optional<String> proxyUsername;
    public final Optional<String> proxyHostname;
    public final Optional<String> originalRequestedHostname;
    public final Optional<String> originalClientAddress;
    public final Optional<String> authorizationEmail;

    public Metadata(final String clientAddress,
                    final String proxyUsername,
                    final String proxyHostname,
                    final String originalRequestedHostname,
                    final String originalClientAddress,
                    final String authorizationEmail) {
        this(clientAddress, ofNullable(proxyUsername), ofNullable(proxyHostname), ofNullable(originalRequestedHostname), ofNullable(originalClientAddress), ofNullable(authorizationEmail));
    }

    public Metadata(final String clientAddress,
                    final Optional<String> proxyUsername,
                    final Optional<String> proxyHostname,
                    final Optional<String> originalRequestedHostname,
                    final Optional<String> originalClientAddress,
                    final Optional<String> authorizationEmail) {
        this.clientAddress = notBlank(clientAddress);
        this.proxyUsername = notNull(proxyUsername);
        this.proxyHostname = notNull(proxyHostname);
        this.originalRequestedHostname = notNull(originalRequestedHostname);
        this.originalClientAddress = notNull(originalClientAddress);
        this.authorizationEmail = notNull(authorizationEmail);
    }
}
