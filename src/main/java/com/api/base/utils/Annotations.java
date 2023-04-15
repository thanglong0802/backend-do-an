package com.api.base.utils;

import com.api.base.utils.validator.EmailValidator;
import com.api.base.utils.validator.ValidDateTimeBeforeValidator;
import com.api.base.utils.validator.ValidTimeBeforeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Annotations {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Operator {
        String value() default "LIKE";

        String field() default "";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface IgnoreField {
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface FilterDate {
        String value() default "FROM";

        String field() default "id";
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface FilterTime {
        String value() default "FROM";

        String field() default "id";
    }

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = { ValidDateTimeBeforeValidator.class })
    @Documented
    public @interface ValidDateTimeBefore {
        String message() default "";

        String fromField() default "";

        String toField() default "";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface ColumnHeader {
        boolean id() default false;

        String value() default "";

        String style() default "";

        int order() default 0;

        int maxLength() default Integer.MAX_VALUE;

        String validLength() default "";

        long maxValue() default Long.MAX_VALUE;

        long minValue() default Long.MIN_VALUE;

        int decimalPlaces() default 0;

        String columnCheckDuplicate() default "";
    }

    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = { EmailValidator.class })
    @Documented
    public @interface Email {
        String message() default "invalid email address";

        int size() default 255;

        String regex() default "";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface DateTime {
        String lesstThan() default "";

        String greaterThan() default "";
    }

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = { ValidTimeBeforeValidator.class })
    @Documented
    public @interface ValidTimeBefore {
        String message() default "";

        String fromField() default "";

        String toField() default "";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }
}
