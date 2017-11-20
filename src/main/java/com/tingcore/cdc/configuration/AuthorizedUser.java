package com.tingcore.cdc.configuration;

import com.tingcore.users.model.UserResponse;

/**
 * @author palmithor
 * @since 2017-10-19
 */
public class AuthorizedUser {

    private UserResponse user;

    public UserResponse getUser() {
        return user;
    }

    public void setUser(final UserResponse userResponse) {
        this.user = userResponse;
    }
}
