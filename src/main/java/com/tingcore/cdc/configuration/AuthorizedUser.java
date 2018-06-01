package com.tingcore.cdc.configuration;

import com.tingcore.commons.api.model.Organization;

/**
 * @author palmithor
 * @since 2017-10-19
 */
public class AuthorizedUser {

    private Long id;
    private String encodedId;
    private Organization organization;

    public AuthorizedUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(final String encodedId) {
        this.encodedId = encodedId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
