package com.tingcore.cdc.infrastructure;

import com.tingcore.cdc.model.AuthorizationId;
import com.tingcore.cdc.model.User;
import com.tingcore.cdc.model.UserId;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.OrganizationResponse;
import com.tingcore.users.model.UserResponse;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import retrofit2.HttpException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.apache.commons.lang3.Validate.notNull;

@Repository
public class UserRepository {
    private final UsersApi usersApi;

    public UserRepository(final UsersApi usersApi) {
        this.usersApi = notNull(usersApi);
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
        return new UserId(userResponse.getId());
    }

    private boolean accountLocked(final UserResponse userResponse) {
        return userResponse.getAttributes().stream()
                .filter(a -> a.getName().equalsIgnoreCase("isLockedOut"))
                .anyMatch(a -> BooleanUtils.toBoolean(a.getAttributeValue().getValue()));
    }

}

