package com.api.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.api.base.exception.ErrorDetail;
import com.api.base.utils.enumerate.Operators;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.CaseFormat;

public class Utilities {
    private Utilities() {
    }

    public static <T> T copyProperties(Object source, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Hibernate5Module());

        try {
            return mapper.readValue(mapper.writeValueAsString(source), clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> copyProperties(List<?> source, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Hibernate5Module());

        try {
            CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return mapper.readValue(mapper.writeValueAsString(source), type);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static <T, K> K updateProperties(T source, K target) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        copyData(source, target, sourceFields, targetFields);
        try {
            Class<?> superClass = source.getClass().getSuperclass();
            sourceFields = superClass.getDeclaredFields();
            copyData(source, target, sourceFields, targetFields);
        } catch (Exception e) {
        }
        return target;
    }

    private static <K, T> void copyData(T source, K target, Field[] sourceFields, Field[] targetFields) {
        for (Field sourceField : sourceFields) {
            try {
                sourceField.setAccessible(true);
                for (Field targetField : targetFields) {
                    try {
                        targetField.setAccessible(true);
                        if (Objects.nonNull(sourceField.get(source)) && sourceField.getName().equals(targetField.getName())
                                && sourceField.getType().equals(targetField.getType())) {
                            targetField.set(target, sourceField.get(source));
                            break;
                        }
                    } catch (IllegalAccessException e) {
                    } finally {
                        targetField.setAccessible(false);
                    }
                }
            } finally {
                sourceField.setAccessible(false);
            }
        }
    }

    public static StringBuilder buildWhereClause(Object o, Map<String, Object> params) {
        StringBuilder whereClause = new StringBuilder();

        Field[] fields = o.getClass().getDeclaredFields();
        Object value = null;
        String fieldName;
        String fieldNameParam;
        for (Field f : fields) {
            String fieldAlias = null;
            try {
                f.setAccessible(true);
                value = f.get(o);
                if (Objects.isNull(value) || value.toString().length() == 0) {
                    continue;
                }
                if (f.isAnnotationPresent(Annotations.IgnoreField.class)) {
                    continue;
                }
                if (f.isAnnotationPresent(Annotations.FilterDate.class)) {
                    if ("FROM".equals(f.getDeclaredAnnotation(Annotations.FilterDate.class).value())) {
                        whereClause.append(" AND " + f.getDeclaredAnnotation(Annotations.FilterDate.class).field() + " >= :" + f.getName());
                        params.put(f.getName(), value);
                    }
                    if ("TO".equals(f.getDeclaredAnnotation(Annotations.FilterDate.class).value())) {
                        whereClause.append(" AND " + f.getDeclaredAnnotation(Annotations.FilterDate.class).field() + " <= :" + f.getName());
                        params.put(f.getName(), value);
                    }
                    continue;
                }
                if (f.isAnnotationPresent(Annotations.FilterTime.class)) {
                    if ("FROM".equals(f.getDeclaredAnnotation(Annotations.FilterTime.class).value())) {
                        whereClause.append(" AND cast(" + f.getDeclaredAnnotation(Annotations.FilterTime.class).field() + " as text) >= :" + f.getName());
                        params.put(f.getName(), value);
                    }
                    if ("TO".equals(f.getDeclaredAnnotation(Annotations.FilterTime.class).value())) {
                        whereClause.append(" AND cast(" + f.getDeclaredAnnotation(Annotations.FilterTime.class).field() + " as text) <= :" + f.getName());
                        params.put(f.getName(), value);
                    }
                    continue;
                }
                if (value.getClass().isEnum()) {
                    value = value.toString();
                }
                String operator = Operators.EQUAL.name();
                if (f.isAnnotationPresent(Annotations.Operator.class)) {
                    operator = f.getDeclaredAnnotation(Annotations.Operator.class).value();
                    fieldAlias = f.getDeclaredAnnotation(Annotations.Operator.class).field();
                }
                fieldName = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(f.getName());
                if (StringUtils.isBlank(fieldAlias)) {
                    fieldAlias = fieldName;
                }
                fieldNameParam = ":" + fieldName;
                if (Operators.IN.equals(Operators.valueOf(operator))) {
                    fieldName = f.getDeclaredAnnotation(Annotations.Operator.class).field();
                    fieldNameParam = ":" + fieldName;
                    whereClause.append(" AND " + fieldAlias + " IN " + fieldNameParam);
                    params.put(fieldName, value);
                } else if (Operators.LIKE.equals(Operators.valueOf(operator))) {
                    whereClause.append(" AND LOWER(" + fieldAlias + ") LIKE " + fieldNameParam);
                    params.put(fieldName, "%" + String.valueOf(f.get(o)).toLowerCase() + "%");
                } else {
                    whereClause.append(" AND " + fieldAlias + " = " + fieldNameParam);
                    params.put(fieldName, value);
                }
            } catch (Exception e) {
            } finally {
                f.setAccessible(false);
            }
        }

        return whereClause;
    }

    public static <T> T returnNullInException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            return null;
        }
    }

    public static Map<String, Object> buildMapParamsFromRequest(HttpServletRequest request) {
        Map<String, Object> parameters = new ConcurrentHashMap<>();
        for (String param : request.getParameterMap().keySet()) {
            String camelCaseParam = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, param);
            String[] lsParams = request.getParameterValues(param);
            if (Objects.isNull(lsParams) || lsParams.length == 0) {
                continue;
            }
            if (lsParams.length == 1) {
                parameters.put(camelCaseParam, request.getParameterValues(param)[0]);
            } else {
                List<String> params = Arrays.asList(lsParams);
                parameters.put(camelCaseParam, params);
            }
        }
        return parameters;
    }

    public static Map<String, String> mapColumnHeaderInClazz(Class<?> clazz) {
        Map<String, String> mapCols = new HashMap<>();

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Annotations.ColumnHeader.class)) {
                Annotations.ColumnHeader header = f.getAnnotation(Annotations.ColumnHeader.class);
                if (StringUtils.isNotBlank(header.value())) {
                    mapCols.put(header.value(), f.getName());
                }
            }
        }
        return mapCols;
    }

    public static Map<Integer, String> mapColumnHeaderWithOrderInClazz(Class<?> clazz) {
        Map<Integer, String> mapCols = new HashMap<>();

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Annotations.ColumnHeader.class)) {
                Annotations.ColumnHeader header = f.getAnnotation(Annotations.ColumnHeader.class);
                if (StringUtils.isNotBlank(header.value())) {
                    mapCols.put(header.order(), header.value());
                }
            }
        }
        return mapCols;
    }

    public static Field findField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (Exception e) {
            // do nothing
        }

        if (field != null) {
            return field;
        }

        if (Object.class != clazz.getSuperclass()) {
            field = findField(clazz.getSuperclass(), fieldName);
        } else {
            for (Field f : clazz.getDeclaredFields()) {
                Type type = f.getGenericType();
                if (type instanceof Class) {
                    String[] arr = fieldName.split("\\.");
                    fieldName = arr[arr.length - 1];
                    field = findField((Class<?>) type, fieldName);
                    if (field != null) {
                        break;
                    }
                } else if (fieldName.equals(f.getName())) {
                    field = f;
                    break;
                }
            }
        }

        return field;
    }

    public static boolean isPositiveNumber(String val, int decimalPlaces) {
        String regex = "^[0-9]+\\.?([0-9]{0," + decimalPlaces + "})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);
        return matcher.matches();
    }

    public static HttpServletResponse buildErrorResponse(HttpServletResponse res, String errorCode, int statusCode, String message, String input) {
        ErrorDetail errorDetail = new ErrorDetail(errorCode, message, input);
        res.setStatus(statusCode);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        try {
            res.setContentType("application/json; charset=UTF-8");
            res.setCharacterEncoding("UTF-8");
            res.getOutputStream().write(mapper.writeValueAsString(errorDetail).getBytes());
        } catch (Exception e) {
        }
        return res;
    }
}
