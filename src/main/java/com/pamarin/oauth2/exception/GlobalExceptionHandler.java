/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.exception;

import com.pamarin.oauth2.model.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jittagornp <http://jittagornp.me>
 * create : 2017/10/03
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidResponseTypeException.class)
    public void unsupportedResponseType(InvalidResponseTypeException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.unsupportedResponseType()
                .returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void invalidRequest(MissingServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.invalidRequest()
                .returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidClientIdException.class)
    public void invalidClient(InvalidClientIdException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.invalidClient()
                .returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidScopeException.class)
    public void invalidScope(InvalidScopeException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.invalidScope()
                .returnError(request, response);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void serverError(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse.serverError()
                .returnError(request, response);
    }

}
