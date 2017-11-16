package com.tingcore.cdc.infrastructure;

import com.google.common.collect.ImmutableList;
import com.tingcore.cdc.model.AuthorizationId;
import com.tingcore.cdc.model.User;
import com.tingcore.cdc.model.UserId;
import com.tingcore.users.api.UsersApi;
import com.tingcore.users.model.AttributeResponse;
import com.tingcore.users.model.AttributeValueResponse;
import com.tingcore.users.model.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private UsersApi usersApi;

    private UserRepository userRepository;

    @Test
    public void testGetUserIdForAuthorizationId() throws Exception {
        final String authorizationId = "1-2-3";
        givenAUserRepository();
        givenAUserExists(authorizationId);
        final Optional<User> user = whenTheUserIsFetchedByAuthorizationId(authorizationId);
        thenTheUserExists(user);
        user.ifPresent(u -> {
            thenTheUserIdIs(u, new UserId(1L));
        });
    }

    private void givenAUserRepository() {
        userRepository = new UserRepository(usersApi);
    }

    private void givenAUserExists(String authorizationId) {
        final boolean getUserAttributes = false;
        when(usersApi.getSelfUsingGET(authorizationId, getUserAttributes)).thenReturn(createUserResponse());
    }

    private Optional<User> whenTheUserIsFetchedByAuthorizationId(String authorizationId) {
        return userRepository.getUserIdForAuthorizationId(new AuthorizationId(authorizationId));
    }

    private void thenTheUserExists(final Optional<User> user) {
        assertThat(user.isPresent()).isTrue();
    }

    private void thenTheUserIdIs(final User user, final UserId expectedUserId) {
        assertThat(user.getUserId()).isEqualTo(expectedUserId);
    }

    private CompletableFuture<UserResponse> createUserResponse() {
        final UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setAttributes(ImmutableList.<AttributeResponse>of(
                isLockedOut(false)
        ));
        return CompletableFuture.completedFuture(userResponse);
    }

    private AttributeResponse isLockedOut(final boolean lockedOut) {
        final AttributeResponse ar = new AttributeResponse();
        ar.setName("isLockedOut");
        final AttributeValueResponse avr = new AttributeValueResponse();
        avr.setValue(Boolean.toString(lockedOut));
        ar.setAttributeValue(avr);
        return ar;
    }

}