package com.nathan.roifmr.configuration;

import com.nathan.roifmr.domain.dto.RequestError;
import com.nathan.roifmr.domain.exchange.QuoteGenerationException;
import com.nathan.roifmr.domain.exchange.QuoteRequestDateValidationException;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice()
public class ControllerExceptionHandlingAdvice {
    Logger log = LoggerFactory.getLogger(ControllerExceptionHandlingAdvice.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestError> handleValidationException(MethodArgumentNotValidException generatedException){
        log.error("Validation failure",generatedException);
        RequestError rtnMessage = new RequestError(HttpStatus.BAD_REQUEST.value(),"Request validation failure");
        generatedException.getBindingResult().getAllErrors().stream().forEach((v)->{
            rtnMessage.getDetails().add(String.format("%s reports:: %s",((FieldError)v).getField(),v.getDefaultMessage()));
        });
        log.error("Validation failure response",rtnMessage);
        return new ResponseEntity<RequestError>(rtnMessage,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(QuoteRequestDateValidationException.class)
    public ResponseEntity<RequestError> handleQuoteRequestValidationException(QuoteRequestDateValidationException validationException){
        log.error("date validation for quote request",validationException);
        RequestError rtnMessage = new RequestError(HttpStatus.NOT_ACCEPTABLE.value(),"Requested quote date range invalid");
        rtnMessage.getDetails().add(validationException.getMessage());
        return new ResponseEntity<RequestError>(rtnMessage,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(QuoteGenerationException.class)
    public ResponseEntity<RequestError> handleQuoteGenerationException(QuoteGenerationException failedQuoteException){
        log.error("requested quote failed to be retrieved",failedQuoteException);
        RequestError rtnMessage = new RequestError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Failed to retrieve generated data");
        rtnMessage.getDetails().add(failedQuoteException.getMessage());
        return new ResponseEntity<>(rtnMessage,HttpStatus.I_AM_A_TEAPOT);
    }
}
