/*
 * Copyright 2017 Pamarin.com
 */
package com.pamarin.oauth2.exception;

import com.pamarin.oauth2.model.ErrorResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
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
    @ExceptionHandler(ServletRequestBindingException.class)
    public void invalidRequest(ServletRequestBindingException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        if (ex.getMessage().startsWith("Missing request header 'Authorization'")) {
            err.setErrorDescription("Require header 'Authorization' as http basic.");
        } else {
            err.setErrorDescription(ex.getMessage());
        }
        err.returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public void invalidRequest(UnsatisfiedServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        err.setErrorDescription("Require parameter '" + ex.getParamConditionGroups().stream().map(m -> StringUtils.join(m, ",")).sorted().collect(Collectors.joining(" or ")) + "'.");
        err.returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRedirectUriException.class)
    public void invalidRequest(InvalidRedirectUriException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        err.setErrorDescription("Invalid format '" + ex.getRedirectUri() + "'.");
        err.returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void invalidRequest(MissingServletRequestParameterException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        err.setErrorDescription("Require parameter " + ex.getParameterName() + " (" + ex.getParameterType() + ").");
        err.returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public void invalidRequest(HttpMediaTypeNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        err.setErrorDescription("Not support media type '" + ex.getContentType() + "'.");
        err.returnError(request, response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void invalidRequest(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ErrorResponse err = ErrorResponse.invalidRequest();
        err.setErrorDescription("Not support http '" + ex.getMethod() + "'.");
        err.returnError(request, response);
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
