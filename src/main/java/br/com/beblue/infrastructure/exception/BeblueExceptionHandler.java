package br.com.beblue.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Beblue Exception Handler - custom exceptions
 *
 * @author Andryev Lemes - 28/02/2019
 */
@Slf4j
@ControllerAdvice
public class BeblueExceptionHandler extends ResponseEntityExceptionHandler {

    public final static String MESSAGES = "messages";

    @ExceptionHandler(BeblueSystemException.class)
    public ResponseEntity<?> handleBeblueSystemException(BeblueSystemException beblueSystemException, WebRequest request) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(MESSAGES, beblueSystemException.getMessages());
        log.error(beblueSystemException.getMessage(), beblueSystemException);
        return handleExceptionInternal(beblueSystemException, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
