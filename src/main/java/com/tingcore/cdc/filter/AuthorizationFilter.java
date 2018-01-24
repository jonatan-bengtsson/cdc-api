package com.tingcore.cdc.filter;

import com.tingcore.cdc.configuration.AuthorizedUser;
import com.tingcore.cdc.configuration.WebMvcConfiguration;
import com.tingcore.commons.api.service.DefaultErrorCode;
import com.tingcore.commons.api.service.HashIdService;
import com.tingcore.commons.rest.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
    public static final String HEADER_CLAIM_USER_ID = "cd-claims-user-id";

    private final HashIdService hashIdService;
    private final FilterUtils filterUtils;

    @Resource(name = WebMvcConfiguration.AUTHORIZED_USER)
    private AuthorizedUser authorizedUser;

    AuthorizationFilter(final HashIdService hashIdService, final FilterUtils filterUtils) {
        this.hashIdService = hashIdService;
        this.filterUtils = filterUtils;
    }


    @Override
    public void init(final FilterConfig filterConfig) {

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
            final String encodedUserId = request.getHeader(HEADER_CLAIM_USER_ID);
            final Optional<Long> idOptional = Optional.ofNullable(encodedUserId)
                    .flatMap(hashIdService::decode);

            if (idOptional.isPresent()) {
                authorizedUser.setEncodedId(encodedUserId);
                authorizedUser.setId(idOptional.get());
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                filterUtils.setError(response, ErrorResponse.unauthorized(), DefaultErrorCode.UNAUTHORIZED);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
