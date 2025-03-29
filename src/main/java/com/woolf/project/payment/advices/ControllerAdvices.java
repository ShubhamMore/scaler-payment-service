package com.woolf.project.payment.advices;

import com.razorpay.RazorpayException;
import com.woolf.project.payment.dtos.ExceptionDTO;
import com.woolf.project.payment.exceptions.RefundInvalidException;
import com.woolf.project.payment.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionDTO> handleRuntimeException(RuntimeException ex){
        ExceptionDTO exceptionDto = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ExceptionDTO> handleNotFoundException(NotFoundException ex){
        ExceptionDTO exceptionDto = new ExceptionDTO(HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RefundInvalidException.class)
    ResponseEntity<ExceptionDTO> handleInvalidRefundException(RefundInvalidException ex){
        ExceptionDTO exceptionDto = new ExceptionDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RazorpayException.class)
    ResponseEntity<ExceptionDTO> handleRazorpayException(RazorpayException ex){
        ExceptionDTO exceptionDto = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
