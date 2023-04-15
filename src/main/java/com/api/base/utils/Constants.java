/**
 *
 */
package com.api.base.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * @author BacDV
 *
 */
public class Constants {

    private Constants() {
    }
    public static final Integer EMAIL_MAX_LENGTH_DEFAULT = 255;
    public static final Integer PAGE_SIZE_DEFAULT = 20;
    public static final Integer PAGE_SIZE_MAX = Integer.MAX_VALUE;
    public static final String SLASH = "/";
    public static final String HAVE_FILE = "Have {0} files";
    public static final String XLSX_BLANK_TEMPLATE = "blank_template.xlsx";

    public static final String BASE_CONTROLLER_PACKAGE = "com.api.base.controller";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String TIME_FORMAT = "HH:mm";
    public static final String FULL_DATETIME_FORMAT_WITH_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FULL_DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String FULL_DATETIME_FORMAT_HYPHEN = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATETIME_FORMAT_HYPHEN = "yyyy-MM-dd";
    public static final String SHORT_DATETIME_FORMAT_SLASH = "dd/MM/yyyy HH:mm:ss";
    public static final String SHORT_DATETIME_SLASH = "dd/MM/yyyy";
    public static final String SHORT_DATETIME_FORMAT_SLASH_YYYY_MM_DD = "yyyy/MM/dd";

    public static final String SHORT_DATE_FORMAT_SLASH = "yyyy/MM/dd";
    public static final String SHORT_DATE_FORMAT_SLASH_YYYYMD = "yyyy/M/d";
    public static final String SHORT_DATE_FORMAT_SLASH_YYYYMDD = "yyyy/M/dd";
    public static final String SHORT_DATE_FORMAT_SLASH_YYYYMMD = "yyyy/MM/d";
    public static final String CURRENT_DATE = "CURRENT_DATE_VALIDATION";

    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Asia/Ho_Chi_Minh");
    public static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String BEARER_TOKEN_SUB = "sub";
    public static final String BEARER_TOKEN_MEMBER_NAME = "mName";
    public static final String ROLE_GUEST = "GUEST";
    public static final String BEARER_TOKEN_TIMEOUT = "token-timeout";
    public static final long REFRESH_TOKEN_IN_TIME = 2 * 60 * 1000;

    public static final int MAX_YEAR = 2099;
    public static final int MAX_MONTH = 12;
    public static final int MAX_DAY = 31;
    public static final int MIN_YEAR = 1900;
    public static final int MIN_MONTH = 1;
    public static final int MIN_DAY = 1;
    public static final Instant MAX_DATE = initMaxDate();
    public static final Instant MIN_DATE = initMinDate();

    private static Instant initMaxDate() {
        return LocalDate.of(MAX_YEAR, MAX_MONTH, MAX_DAY).atStartOfDay(Constants.DEFAULT_ZONE_ID).toInstant().plus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).minusSeconds(1);
    }

    private static Instant initMinDate() {
        return LocalDate.of(MIN_YEAR, MIN_MONTH, MIN_DAY).atStartOfDay(Constants.DEFAULT_ZONE_ID).toInstant();
    }
}
