package com.api.base.exception;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String field;
    private String message;
    private Integer loginFailCount;
    private Class<?> clazz;
    private Object[] params;

    public ErrorDetail(String code) {
        super();
        this.code = code;
    }

    public ErrorDetail(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ErrorDetail(String code, Integer loginFailCount, String message) {
        super();
        this.code = code;
        this.message = message;
        this.loginFailCount = loginFailCount;
    }

    public ErrorDetail(String code, String field, String message) {
        super();
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public ErrorDetail(String code, Class<?> clazz, String field, Object...params) {
        super();
        this.code = code;
        this.clazz = clazz;
        this.field = field;
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Integer loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

}
