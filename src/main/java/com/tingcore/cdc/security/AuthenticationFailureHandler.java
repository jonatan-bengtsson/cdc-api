package com.tingcore.cdc.security;

import com.tingcore.library.eventprocessing.EventManager;
import com.tingcore.library.eventprocessing.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.Validate.notNull;

@Component
public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private final EventSource eventSource;

    @Autowired
    public AuthenticationFailureHandler(final EventManager eventManager) {
        this.eventSource = notNull(eventManager).createSource(getClass());
    }

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {
        eventSource.fireEvent(new AuthorizationFailedEvent(exception.getClass().getSimpleName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
