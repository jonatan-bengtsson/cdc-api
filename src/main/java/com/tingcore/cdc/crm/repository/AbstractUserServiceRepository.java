package com.tingcore.cdc.crm.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tingcore.commons.api.repository.AbstractApiRepository;
import com.tingcore.commons.rest.Pagination;
import com.tingcore.commons.rest.PagingCursor;
import com.tingcore.users.model.Paginationlong;
import com.tingcore.users.model.PagingCursorlong;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

/**
 * @author palmithor
 * @since 2017-12-15
 */
abstract class AbstractUserServiceRepository extends AbstractApiRepository {


    private Integer defaultTimeOut;

    AbstractUserServiceRepository(final ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Value("${app.user-service.default-timeout}")
    public void setDefaultTimeOut(final Integer defaultTimeOut) {
        this.defaultTimeOut = defaultTimeOut;
    }

    @Override
    public Integer getTimeout() {
        return defaultTimeOut;
    }


    Pagination<Long> convertGeneratedPagination(final Paginationlong responsePagination) {
        return Optional.ofNullable(responsePagination)
                .map(pagination -> {
                    Pagination<Long> result = new Pagination<>();
                    Optional.ofNullable(pagination.getPrev()).ifPresent(pagingCursor -> result.setPrev(convertGeneratedCursor(pagingCursor)));
                    Optional.ofNullable(pagination.getNext()).ifPresent(pagingCursor -> result.setNext(convertGeneratedCursor(pagingCursor)));
                    return result;
                })
                .orElse(null);
    }

    private PagingCursor<Long> convertGeneratedCursor(final PagingCursorlong pagingCursor) {
        return new PagingCursor<>(pagingCursor.getSortField(), pagingCursor.getSortFieldCursor(), pagingCursor.getSortFieldSortOrder(), pagingCursor.getIdField(), pagingCursor.getIdFieldCursor(), pagingCursor.getIdFieldSortOrder());
    }
}
