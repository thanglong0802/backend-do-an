package com.api.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class MessageUtil {

    @Autowired
    private MessageSource msgSource;

    /**
     * Get message
     * 
     * @param key
     * @return
     */
    public String getMessage(String key) {
        return getMessage(key, "");
    }

    /**
     * Get message with parameters
     *
     * @param key
     * @param params
     * @return
     */
    public String getMessage(String key, Object... params) {
        List<String> paramStrs = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (Object param : params) {
                paramStrs.add(String.valueOf(param));
            }
        }
        return msgSource.getMessage(key, paramStrs.toArray(), LocaleContextHolder.getLocale());
    }

    public String getMessage(FieldError field) {
        return msgSource.getMessage(field, LocaleContextHolder.getLocale());
    }
}
