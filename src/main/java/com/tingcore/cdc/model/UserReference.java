package com.tingcore.cdc.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.length;
import static org.apache.commons.lang3.Validate.isTrue;

public class UserReference implements Externalizable {
    public final int COGNITO_IDENTITY_LENGTH = 55;
    public final Pattern COGNITO_IDENTITY_REGEX = Pattern.compile(format("[0-9a-f-]{1,%d}", COGNITO_IDENTITY_LENGTH));

    public final String value;

    public UserReference(final String value) {
        isTrue(COGNITO_IDENTITY_LENGTH >= length(value), format("Expected Cognito identity value to be of max length %d", COGNITO_IDENTITY_LENGTH));
        isTrue(COGNITO_IDENTITY_REGEX.matcher(value).matches(), format("Expected Cognito identity value to match regex [%s]", COGNITO_IDENTITY_REGEX.toString()));

        this.value = value;
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
