package com.tingcore.cdc.charging.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.tingcore.cdc.charging.model.AuthorizationToken;
import com.tingcore.cdc.charging.model.ChargePointId;
import com.tingcore.cdc.charging.model.CustomerKeyId;
import com.tingcore.cdc.charging.model.TrustedUserId;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.payments.emp.api.TokensApi;
import com.tingcore.payments.emp.model.ApiAuthorizationToken;
import com.tingcore.payments.emp.model.Authorization;
import com.tingcore.payments.emp.model.CreateAuthorizationTokenRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;

import java.time.Instant;

import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class TokenRepository extends AbstractApiRepository {
    private final TokensApi tokensApi;
    private static final Integer DEFAULT_TIME_OUT = 60;

    public TokenRepository(final ObjectMapper objectMapper,
                           final TokensApi tokensApi) {
        super(notNull(objectMapper));
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
            request.setChargePoint(chargePointId.id);
            request.setTime(Instant.now().toEpochMilli());
            return apiTokenToModel(execute(tokensApi.createAuthorizationToken(request)).getResponse());
        } catch (final RestClientException exception) {
            throw new RuntimeException(exception); // TODO better error handling
        }
    }

    private AuthorizationToken apiTokenToModel(final ApiAuthorizationToken apiAuthorizationToken) {
        return new AuthorizationToken(apiAuthorizationToken.getValue());
    }

    @Override
    public Integer getTimeout() {
        return DEFAULT_TIME_OUT;
    }
}
