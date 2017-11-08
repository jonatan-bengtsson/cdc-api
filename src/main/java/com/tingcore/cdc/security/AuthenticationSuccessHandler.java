package com.tingcore.cdc.security;

import com.tingcore.library.eventprocessing.EventManager;
import com.tingcore.library.eventprocessing.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private final EventSource eventSource;

    @Autowired
    public AuthenticationSuccessHandler(final EventManager eventManager) {
        this.eventSource = notNull(eventManager).createSource(getClass());
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) {
        eventSource.fireEvent(new AuthorizationIssuedEvent());
    }
}
