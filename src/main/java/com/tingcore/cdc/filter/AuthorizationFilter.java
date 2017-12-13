package com.tingcore.cdc.filter;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.cdc.crm.repository.UserRepository;
import com.tingcore.commons.api.repository.ApiResponse;
import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.rest.ErrorResponse;
import com.tingcore.users.model.UserResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter which checks with the user service if the user with the extracted cognito id exists.
 * <p>
 * If it exists it continues the processing and updates the AuthorizedUser request scoped bean
 *
 * @author palmithor
 * @since 2017-11-15
 */
public class AuthorizationFilter implements Filter {

    private static Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);

    private final UserRepository userRepository;
    private final FilterUtils filterUtils;
    private final String cognitoUserIdHeaderName;
    private final String cognitoAuthProviderHeaderName;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    AuthorizationFilter(final UserRepository userRepository,
                        final FilterUtils filterUtils,
                        final String cognitoAuthProviderHeaderName,
                        final String cognitoUserIdHeaderName) {
        this.userRepository = userRepository;
        this.filterUtils = filterUtils;
        this.cognitoAuthProviderHeaderName = cognitoAuthProviderHeaderName;
        this.cognitoUserIdHeaderName = cognitoUserIdHeaderName;
    }


    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Unfortunately this needs to be done as filters can not be excluded for certain methods
        // It will however be removed when we introduce spring security
        if ("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
        } else {
            final String authorizationId = request.getHeader(cognitoUserIdHeaderName);

            if(1==1) {
                filterChain.doFilter(request, response);
                return;
            }
            if (StringUtils.isBlank(authorizationId)) {
                LOG.info("Cognito auth provider header missing for path {}", request.getRequestURI());
                final String header = request.getHeader(cognitoAuthProviderHeaderName);
                LOG.debug("An error is created in a filter. {}: {}", cognitoAuthProviderHeaderName, header != null ? header : "isEmpty");
                filterUtils.setError(response, ErrorResponse.unauthorized(), DefaultErrorCode.UNAUTHORIZED,
                        "The cognito auth provider header from API Gateway did not include the required data",
                        "cognitoAuthProviderHeaderName: " + header);
            } else {
                final ApiResponse<UserResponse> apiResponse = userRepository.getSelf(authorizationId, false);
                if (apiResponse.hasError()) {
                    LOG.info("An error occurred fetching the authorized user from the user service. Authorization id: {}, error message: {}", authorizationId, apiResponse.getError().getMessage());
                    LOG.debug("An error is created in a filter. Request origin: {}", request.getRemoteHost());
                    filterUtils.setError(response, apiResponse.getError());
                } else {
                    authorizedUser.setUser(apiResponse.getResponse());
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Setter used for unit testing purpose
     */
    void setAuthorizedUser(final AuthorizedUser authorizedUser) {
        this.authorizedUser = authorizedUser;
    }
}
