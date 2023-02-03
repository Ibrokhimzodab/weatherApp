package com.weather.app.exeptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class ApplicationExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomGeneralException.class})
    public ResponseEntity<Object> generalExceptionHandler(CustomGeneralException ex,
                                                          WebRequest request) {
        CustomErrorModel response = new CustomErrorModel(ex.getMessage(), ex.getCode(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Object> handleInvalidRequests(InvalidRequestException ex,
                                                        WebRequest request) {
        CustomErrorModel response =
                new CustomErrorModel(ex.getMessage(), ex.getCode(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundRequestException.class})
    public ResponseEntity<Object> handleNotFoundRequests(NotFoundRequestException ex,
                                                         WebRequest request) {
        CustomErrorModel response =
                new CustomErrorModel(ex.getMessage(), ex.getCode(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UnauthorizedRequestException.class})
    public ResponseEntity<Object> handleUnauthenticatedExceptionHandler(CustomGeneralException ex,
                                                                        WebRequest request) {
        CustomErrorModel response =
                new CustomErrorModel(ex.getMessage(), ex.getCode(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ForbiddenRequestException.class})
    public ResponseEntity<Object> handleForbiddenExceptionHandler(CustomGeneralException ex,
                                                                  WebRequest request) {
        CustomErrorModel response =
                new CustomErrorModel(ex.getMessage(), ex.getCode(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorModel response =
                new CustomErrorModel(ex.getMessage(), "0000", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex) {
        CustomErrorModel response =
                new CustomErrorModel("Invalid input parameters: " + ex.getMessage(), "1003",
                        HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(ex);
        String message = ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" |\n"));
        CustomErrorModel response = new CustomErrorModel(message , "0000", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(
            UsernameNotFoundException ex) {
        CustomErrorModel response =
                new CustomErrorModel("No user with given username found", "0000",
                        HttpStatus.FORBIDDEN.value());
        return ResponseEntity.badRequest().body(response);
    }


}
