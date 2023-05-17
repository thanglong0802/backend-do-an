package com.api.base.exception;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.base.utils.MessageUtil;
import com.api.base.utils.enumerate.MessageCode;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    HttpServletRequest req;

    @Autowired
    MessageUtil messageUtil;

    @ExceptionHandler(value = BadAuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleBadAuthenticationException(BadAuthenticationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<ErrorDetail> errors = new ArrayList<>();

        Object[] params = {};
        ErrorDetail errorDetail = new ErrorDetail(ex.getCode(), messageUtil.getMessage(ex.getCode(), params));
        errors.add(errorDetail);

        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleBusinessException(BusinessException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<ErrorDetail> errors = new ArrayList<>();

        Object[] params = ex.getParams();
        ErrorDetail errorDetail = new ErrorDetail(ex.getErrorCode(), messageUtil.getMessage(ex.getErrorCode(), params));
        errors.add(errorDetail);

        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<ErrorDetail> errors = new ArrayList<>();

        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError objectError : objectErrors) {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                ErrorDetail errorDetail = new ErrorDetail(null, fieldError.getField(), fieldError.getDefaultMessage());
                errors.add(errorDetail);
            }
        }

        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<ErrorDetail> errors = new ArrayList<>();

        Object[] params = {};
        ErrorDetail errorDetail = new ErrorDetail(MessageCode.ERR_401.name(), messageUtil.getMessage(MessageCode.ERR_401.name(), params));
        errors.add(errorDetail);

        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<ErrorDetail> errors = new ArrayList<>();
        errorMessage.setErrors(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}