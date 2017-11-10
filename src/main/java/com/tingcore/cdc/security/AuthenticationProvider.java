package com.tingcore.cdc.security;

import com.tingcore.cdc.constant.SpringProfilesConstant;
import com.tingcore.cdc.infrastructure.UserRepository;
import com.tingcore.cdc.model.AuthorizationId;
import com.tingcore.cdc.model.security.Token;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

@Component
@Profile({SpringProfilesConstant.TEST, SpringProfilesConstant.STAGE, SpringProfilesConstant.PROD})
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final UserRepository userRepository;

    public AuthenticationProvider(final UserRepository userRepository) {
        this.userRepository = notNull(userRepository);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (Token.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails,
                                                  final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(final String username,
                                       final UsernamePasswordAuthenticationToken token) throws AuthenticationException {
        return Optional.ofNullable(AuthorizationId.class.cast(token.getPrincipal()))
                .map(this::fetchUserDetails)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Authorization id was not found in request"));
    }

    private UserDetails fetchUserDetails(final AuthorizationId authorizationId) {
        return userRepository.getUserIdForAuthorizationId(authorizationId)
                .orElseThrow(() -> new BadCredentialsException("No user found for the supplied authorization id"));
    }

}
