package com.tingcore.cdc.security;

import com.tingcore.cdc.configuration.SecurityConfiguration.SecurityProperties;
import com.tingcore.cdc.model.AuthorizationId;
import com.tingcore.cdc.model.security.Metadata;
import com.tingcore.cdc.model.security.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SecurityProperties securityProperties;

    public AuthenticationFilter(final RequestMatcher requestMatcher) {
        super(requestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) {
        final Metadata metadata = (Metadata) this.authenticationDetailsSource.buildDetails(request);
        final String authorizationId = request.getHeader(securityProperties.header());
        final Token token = new Token(Optional.ofNullable(authorizationId).map(AuthorizationId::new).orElse(null), metadata);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain chain,
                                            final Authentication authentication)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);

        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        chain.doFilter(request, response);
    }

}
