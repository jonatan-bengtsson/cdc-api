package com.tingcore.cdc.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.apache.commons.lang3.Validate.notNull;

public class User extends org.springframework.security.core.userdetails.User {
    private final UserId userId;

    public User(final UserId userId,
                final boolean enabled,
                final boolean accountNonExpired,
                final boolean credentialsNonExpired,
                final boolean accountNonLocked,
                final Collection<? extends GrantedAuthority> authorities) {
        super(notNull(userId).value.toString(), "", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, notNull(authorities));
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }
}
