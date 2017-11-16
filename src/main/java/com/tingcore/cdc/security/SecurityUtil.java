package com.tingcore.cdc.security;

import com.tingcore.cdc.model.UserId;
import com.tingcore.cdc.model.security.Metadata;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static UserId currentUser() {
        return UserId.class.cast(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public static Metadata currentMetadata() {
        return Metadata.class.cast(SecurityContextHolder.getContext().getAuthentication().getDetails());
    }
}
