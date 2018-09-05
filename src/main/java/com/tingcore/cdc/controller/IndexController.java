package com.tingcore.cdc.controller;

import com.tingcore.cdc.constant.ErrorCode;
import com.tingcore.cdc.service.MessageByLocaleService;
import com.tingcore.commons.rest.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author palmithor
 * @since 2017-05-11.
 */
@RestController
public class IndexController implements ErrorController {

    private final MessageByLocaleService messageByLocaleService;

    @Autowired
    public IndexController(final MessageByLocaleService messageByLocaleService) {
        this.messageByLocaleService = messageByLocaleService;
    }


    @RequestMapping(value = "/error")
    public ErrorResponse error() {
        ErrorCode errorCode = ErrorCode.URL_NOT_FOUND;
        return ErrorResponse.notFound()
                .code(errorCode.value())
                .message(messageByLocaleService.getMessage(errorCode.messageKey()))
                .build();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
