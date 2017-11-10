package com.tingcore.cdc.infrastructure;

import com.tingcore.cdc.common.Reference;
import com.tingcore.cdc.common.Reference.Entity;
import com.tingcore.cdc.common.Reference.Service;
import com.tingcore.cdc.common.Reference.Version;
import com.tingcore.cdc.configuration.IntegrationConfiguration.UserServiceInformation;
import com.tingcore.cdc.model.AuthorizationId;
import com.tingcore.cdc.model.User;
import com.tingcore.cdc.model.UserId;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.UserResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import retrofit2.HttpException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class UserRepository {

    private final UsersApi usersApi;
    private final UserServiceInformation userServiceInformation;

    public UserRepository(final UsersApi usersApi,
                          final UserServiceInformation userServiceInformation) {
        this.usersApi = notNull(usersApi);
        this.userServiceInformation = notNull(userServiceInformation);
    }

    public Optional<User> getUserIdForAuthorizationId(final AuthorizationId authorizationId) {
        try {
            //TODO: use circuit breaker here
            return Optional.of(toDomain(usersApi.getSelfUsingGET(authorizationId.value).get(10, TimeUnit.SECONDS)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while waiting for response from user service", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof HttpException) {
                if (HttpException.class.cast(e.getCause()).code() == HttpStatus.UNAUTHORIZED.value()) {
                    return Optional.empty();
                }
            }
            throw new RuntimeException("Failed to fetch user from user service", e);
        } catch (TimeoutException e) {
            throw new RuntimeException("Failed to fetch user from user service", e);
        }
    }

    private User toDomain(final UserResponse userResponse) {
        return new User(
                createUserId(userResponse),
                true,
                true,
                true,
                !accountLocked(userResponse),
                emptySet());
    }

    // This should be provided by the user service, but until then we create it ourselves
    private UserId createUserId(final UserResponse userResponse) {
        final Version version = new Version("v1", new Service(userServiceInformation.getHostname()));
        final Entity entityReference = new Entity(userResponse.getId(), new Reference.Collection("users", version));
        return new UserId(entityReference.asString());
    }

    private boolean accountLocked(final UserResponse userResponse) {
        return userResponse.getAttributes().stream()
                .filter(a -> a.getName().equalsIgnoreCase("isLockedOut"))
                .anyMatch(a -> BooleanUtils.toBoolean(a.getAttributeValue().getValue()));
    }

}

