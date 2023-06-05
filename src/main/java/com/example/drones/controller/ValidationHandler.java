package com.example.drones.controller;

import com.example.drones.constante.Constante;
import com.example.drones.dto.ResultDataResDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request)
    {
        ObjectError err = null;
        for(ObjectError error: ex.getBindingResult().getAllErrors()){
            err = error;
            break;
        }
        ResultDataResDto resultDataResDto = new ResultDataResDto();
        DroneController.responseDto(resultDataResDto,
                Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID,
                err.getDefaultMessage(),
                Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID,
                err.getObjectName()+" "+((FieldError) err).getField());

        return new ResponseEntity<>(resultDataResDto, HttpStatus.BAD_REQUEST);
    }

}
