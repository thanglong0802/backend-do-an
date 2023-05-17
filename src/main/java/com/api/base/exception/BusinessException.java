package com.api.base.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorCode;
    private Object[] params;
    private List<ErrorDetail> errorDetails;
    private ErrorDetail errorDetail;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorCode) {
        this.errorCode = errorCode;
        this.params = null;
        this.errorDetails = new ArrayList<>();
        this.errorDetail = null;
    }

    public BusinessException(String errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
        this.errorDetails = new ArrayList<>();
        this.errorDetail = null;
    }

    public BusinessException(List<ErrorDetail> errorDetails) {
        this.errorCode = null;
        this.params = null;
        this.errorDetails = errorDetails;
        this.errorDetail = null;
    }

    public BusinessException(ErrorDetail errorDetail) {
        this.errorCode = null;
        this.params = null;
        this.errorDetails = new ArrayList<>();
        this.errorDetail = errorDetail;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getParams() {
        return params;
    }

    public List<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setErrorDetails(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }

    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }
}
