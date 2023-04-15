package com.api.base.utils.validator;

import com.api.base.utils.Annotations;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidTimeBeforeValidator implements ConstraintValidator<Annotations.ValidTimeBefore, Object> {

    private String fromField;
    private String toField;
    private String message;

    @Override
    public void initialize(Annotations.ValidTimeBefore constraintAnnotation) {
        message = constraintAnnotation.message();
        fromField = constraintAnnotation.fromField();
        toField = constraintAnnotation.toField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (ObjectUtils.allNull(value)) {
            return isValid;
        }
        try {
            final String from = BeanUtils.getProperty(value, fromField);
            final String to = BeanUtils.getProperty(value, toField);
            if (StringUtils.isNotBlank(from) && StringUtils.isNotBlank(to)) {
                if (ObjectUtils.allNull(from) || ObjectUtils.allNull(to) || from.compareTo(to) > 0) {
                    isValid = false;
                }
            } else {
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

}

