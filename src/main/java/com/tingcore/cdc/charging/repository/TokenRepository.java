package com.tingcore.cdc.charging.repository;

import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.AuthorizationToken;
import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.model.UserReference;
import com.tingcore.payments.emp.api.TokensApi;
import com.tingcore.payments.emp.model.ApiAuthorizationToken;
import com.tingcore.payments.emp.model.Authorization;
import com.tingcore.payments.emp.model.CreateAuthorizationTokenRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class TokenRepository {
    private final TokensApi tokensApi;

    public TokenRepository(final TokensApi tokensApi) {
        this.tokensApi = notNull(tokensApi);
    }

    public AuthorizationToken createToken(final UserReference userReference,
                                          final CustomerKeyId customerKeyId,
                                          final ChargePointId chargePointId) {
        try {
            final CreateAuthorizationTokenRequest request = new CreateAuthorizationTokenRequest();
            final Authorization authorization = new Authorization();
            authorization.setMethod(Authorization.MethodEnum.AWS_COGNITO_ID);
            authorization.setData(ImmutableMap.of("id", userReference.value));
            request.setAuthorization(authorization);
            // TODO set reference to customer key here, not supported yet
            // request.setUser(customerKey.value);
            request.setChargePoint(chargePointId.value);
            return apiTokenToModel(tokensApi.createAuthorizationToken(request).execute().body());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private AuthorizationToken apiTokenToModel(final ApiAuthorizationToken apiAuthorizationToken) {
        return new AuthorizationToken(apiAuthorizationToken.getValue());
    }
}
