package com.api.base.utils;


import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static Instant getCurrentTimeUTC() {
        return ZonedDateTime.now(Constants.DEFAULT_ZONE_ID).toInstant().truncatedTo(ChronoUnit.SECONDS);
    }

    public static boolean isBeforeDate(Instant startDate, Instant endDate) {
        return startDate.isBefore(endDate);
    }

    public static boolean isEqualOrBeforeDate(Instant startDate, Instant endDate){
        return startDate.compareTo(endDate) <= 0;
    }

    public static boolean isEqualDate(Instant firstDate, Instant secondDate){
        return firstDate.equals(secondDate);
    }

    public static Timestamp convertInstantToTimestamp(Instant date) {
        return Timestamp.valueOf(LocalDateTime.ofInstant(date, Constants.DEFAULT_ZONE_ID));
    }

    public static Instant convertFromDateStr(String value, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
            return localDateTime.atZone(Constants.DEFAULT_ZONE_ID).toInstant();
        } catch (Exception ex) {
            return null;
        }
    }

    public static Instant getFirstDayOfQuarter(int year, int quarter) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, (quarter - 1) * 3);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.toInstant();
    }

    public static Instant getFirstDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.toInstant();
    }

    public static Instant getLastDayOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.toInstant();
    }

    public static String convertInstantToString(Instant date, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(Constants.DEFAULT_ZONE_ID);
            return formatter.format(date);
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static Instant getWorkingDayXth(int numberWork, int month, List<Instant> holidays) {
        int numDay = 0;
        Calendar dayOfMonth = getFirstDayOfMonth(month);
        for (int i = 1; i <= dayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayOfMonth.set(Calendar.DAY_OF_MONTH, i);
            if (isWorkingDay(dayOfMonth, holidays)) {
                numDay++;
            }
            if (numDay == numberWork) {
                return dayOfMonth.toInstant();
            }
        }
        return null;
    }

    public static Instant geLastWorkingDayBetweenDate(int month, int fromDayOfMonth, int lastDayOfMonth, List<Instant> holidays) {
        Instant result = null;
        Calendar dayOfMonth = getFirstDayOfMonth(month);
        for (int i = lastDayOfMonth; i >= fromDayOfMonth; i--) {
            dayOfMonth.set(Calendar.DAY_OF_MONTH, i);
            if (isWorkingDay(dayOfMonth, holidays)) {
                result = dayOfMonth.toInstant();
                break;
            }
        }
        return result;
    }

    public static Calendar getFirstDayOfMonth(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    private static boolean isWorkingDay(Calendar cal, List<Instant> holidays) {
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day == 1 || day == 7) {
            return false;
        }
        Instant date = cal.toInstant().truncatedTo(ChronoUnit.DAYS);
        for (Instant instant : holidays) {
            if (instant.truncatedTo(ChronoUnit.DAYS).compareTo(date) == 0) {
                return false;
            }
        }
        return true;
    }

    public static Integer getYearFromInstant(Instant date) {
        return date.atZone(Constants.DEFAULT_ZONE_ID).toLocalDate().getYear();
    }

    public static Instant formatDateStrToInstantSameZone(String format, String value) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(value);
            return date.toInstant();
        } catch (Exception ex) {
            return null;
        }
    }

    public static Instant convertStringHourToInstant(String hourMinute) {
        if (StringUtils.isAllBlank(hourMinute)) {
            return null;
        }
        DateTimeFormatter fmt;
        Instant result;
        String time = "1970-01-01T" + hourMinute + ":00.000Z";
        try {
            fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_WITH_ZONE).withZone(Constants.DEFAULT_ZONE_ID);
            result = Instant.from(fmt.parse(time));
        } catch (Exception e) {
            try {
                fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            } catch (Exception ex) {
                fmt = DateTimeFormatter.ofPattern(Constants.SHORT_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            }
        }
        return result;
    }

    public static Instant convertTimeStringToInstant(String timeStr) {
        if (StringUtils.isAllBlank(timeStr)) {
            return null;
        }
        DateTimeFormatter fmt;
        Instant result;
        String time = "1970-01-01T" + timeStr + ".000Z";
        try {
            fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_WITH_ZONE).withZone(Constants.DEFAULT_ZONE_ID);
            result = Instant.from(fmt.parse(time));
        } catch (Exception e) {
            try {
                fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            } catch (Exception ex) {
                fmt = DateTimeFormatter.ofPattern(Constants.SHORT_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            }
        }
        return result;
    }

    public static Instant convertDateStringToInstant(String dateStr) {
        if (StringUtils.isAllBlank(dateStr)) {
            return null;
        }
        DateTimeFormatter fmt;
        Instant result;
        String time = dateStr + "T00:00:00.000Z";
        try {
            fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_WITH_ZONE).withZone(Constants.DEFAULT_ZONE_ID);
            result = Instant.from(fmt.parse(time));
        } catch (Exception e) {
            try {
                fmt = DateTimeFormatter.ofPattern(Constants.FULL_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            } catch (Exception ex) {
                fmt = DateTimeFormatter.ofPattern(Constants.SHORT_DATETIME_FORMAT_HYPHEN).withZone(Constants.DEFAULT_ZONE_ID);
                result = Instant.from(fmt.parse(time));
            }
        }
        return result;
    }
}

