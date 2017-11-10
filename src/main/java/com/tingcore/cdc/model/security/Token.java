package com.tingcore.cdc.model.security;

import com.tingcore.cdc.model.AuthorizationId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Token extends UsernamePasswordAuthenticationToken implements Externalizable {

    public Token(final AuthorizationId authorizationId,
                 final Metadata metadata) {
        super(authorizationId, null);
        setDetails(metadata);
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        ensureThatThisWillNeverHappen();
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        ensureThatThisWillNeverHappen();
    }

    private static void ensureThatThisWillNeverHappen() {
        throw new UnsupportedOperationException("This class is not intended to be serialized.");
    }

}
