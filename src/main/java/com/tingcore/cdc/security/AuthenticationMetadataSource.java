package com.tingcore.cdc.security;

import com.tingcore.cdc.model.security.Metadata;
import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class AuthenticationMetadataSource implements AuthenticationDetailsSource<HttpServletRequest, Metadata> {

    @Override
    public Metadata buildDetails(final HttpServletRequest context) {
        return AuthenticationMetadataBuilder.fromRequest(context);
    }

    static class AuthenticationMetadataBuilder {

        private static final String REMOTE_PROXY_USER_HEADER = "Proxy-Remote-User";
        private static final String X_FORWARDED_FOR = "x-forwarded-for";
        private static final String X_FORWARDED_HOST = "x-forwarded-host";
        private static final String X_FORWARDED_SERVER = "x-forwarded-server";
        private static final String X_AUTHORIZATION_EMAIL = "X-Authorization-Email";

        static Metadata fromRequest(final HttpServletRequest request) {
            return new Metadata(
                    request.getRemoteAddr(),
                    header(request, REMOTE_PROXY_USER_HEADER),
                    header(request, X_FORWARDED_SERVER),
                    header(request, X_FORWARDED_HOST),
                    header(request, X_FORWARDED_FOR),
                    header(request, X_AUTHORIZATION_EMAIL)
            );
        }

        private static Optional<String> header(final HttpServletRequest request,
                                               final String headerName) {
            return ofNullable(request.getHeader(headerName))
                    .map(String::trim);
        }
    }
}
