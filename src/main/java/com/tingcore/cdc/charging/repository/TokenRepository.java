package com.tingcore.cdc.charging.repository;

import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.AuthorizationToken;
import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.model.TrustedUserId;
import com.tingcore.payments.emp.api.TokensApi;
import com.tingcore.payments.emp.model.ApiAuthorizationToken;
import com.tingcore.payments.emp.model.Authorization;
import com.tingcore.payments.emp.model.CreateAuthorizationTokenRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class TokenRepository {
    private final TokensApi tokensApi;

    public TokenRepository(final TokensApi tokensApi) {
        this.tokensApi = notNull(tokensApi);
    }

    public AuthorizationToken createToken(final TrustedUserId trustedUserId,
                                          final CustomerKeyId customerKeyId,
                                          final ChargePointId chargePointId) {
        notNull(trustedUserId);
        notNull(customerKeyId);
        notNull(chargePointId);
        try {
            final CreateAuthorizationTokenRequest request = new CreateAuthorizationTokenRequest();
            final Authorization authorization = new Authorization();
            authorization.setMethod(Authorization.MethodEnum.TRUSTED_USER);
            authorization.setData(ImmutableMap.of("id", trustedUserId.value));
            request.setAuthorization(authorization);
            request.setAccount(customerKeyId.value);
            request.setChargePoint(chargePointId.value);
            request.setTime(Instant.now().toEpochMilli());
            return apiTokenToModel(tokensApi.createAuthorizationToken(request));
        } catch (final RestClientException exception) {
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    private AuthorizationToken apiTokenToModel(final ApiAuthorizationToken apiAuthorizationToken) {
        return new AuthorizationToken(apiAuthorizationToken.getValue());
    }
}
