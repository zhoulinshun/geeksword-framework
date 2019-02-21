package org.geeksword.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


/**
 * @Author: 周林顺
 * @Description:
 * @Date: Created in 2018/3/28.
 */
public class DateUtil {

    private static final ZoneId SP = ZoneId.of("America/Sao_Paulo");

    private static final DateTimeFormatter default_formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;


    public static String format(Date date) {
        return format(date, default_formatter);
    }

    public static String format(Date date, String pattern) {
        return format(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(Date date, String pattern, ZoneId zoneId) {
        return format(date, DateTimeFormatter.ofPattern(pattern), zoneId);
    }

    public static String format(Date date, DateTimeFormatter dateTimeFormatter) {
        return format(date, dateTimeFormatter, ZoneOffset.systemDefault());
    }

    public static String format(Date date, DateTimeFormatter formatter, String zoneId) {
        return format(date, formatter, ZoneId.of(zoneId));
    }

    /**
     * 日期转指定时区字符串
     *
     * @param date
     * @param formatter 格式器
     * @param zoneId    时区
     * @return
     */
    public static String format(Date date, DateTimeFormatter formatter, ZoneId zoneId) {
        if (date==null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), zoneId).format(formatter);
    }

    /**
     * 日期字符串转另一个格式字符串
     *
     * @param date       原日期字符串
     * @param pattern    原日期字符串格式
     * @param newPattern 目标日期字符串格式
     * @return 目标字符串
     */
    public static String dateStrToDateStr(String date, String pattern, String newPattern) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern)).format(DateTimeFormatter.ofPattern(newPattern));
    }

    public static Date dateStrToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return (sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date addHours(Date date, int hour) {
        return add(date, hour, ChronoUnit.HOURS);
    }

    public static Date addDays(Date date, int day) {
        return add(date, day, ChronoUnit.DAYS);
    }

    public static Date addWeeks(Date date, int week) {
        return add(date, week, ChronoUnit.WEEKS);
    }

    public static Date addMonths(Date date, int month) {
        return add(date, month, ChronoUnit.MONTHS);
    }

    public static Date addYears(Date date, int year) {
        return add(date, year, ChronoUnit.YEARS);
    }

    public static Date add(Date date, int time, ChronoUnit chronoUnit) {
        switch (chronoUnit) {
            case NANOS:
            case MICROS:
            case MILLIS:
            case SECONDS:
            case MINUTES:
            case HOURS:
            case HALF_DAYS:
            case DAYS:
                return Date.from(date.toInstant().plus(time, chronoUnit));
            default:
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
                LocalDateTime plus = localDateTime.plus(time, chronoUnit);
                return Date.from(plus.toInstant(ZoneOffset.UTC));
        }
    }

    /**
     * 当前时区转圣保罗时区
     * @param date
     * @return
     */
    public static Date toSPDate(Date date) {
        return toZoneDate(date, SP);
    }

    /**
     * 当前时区转utc时区
     * @param date
     * @return
     */
    public static Date toUTCDate(Date date) {
        return toZoneDate(date, ZoneOffset.UTC);
    }

    public static Date toZoneDate(Date date, ZoneId zoneId) {
        return toZoneDate(date, ZoneId.systemDefault(), zoneId);
    }

    /**
     * 时区时间转另一时区时间
     *
     * @param date
     * @param sourceZonId
     * @param targetZoneId
     * @return
     */
    public static Date toZoneDate(Date date, ZoneId sourceZonId, ZoneId targetZoneId) {
        int target = date.toInstant().atZone(targetZoneId).getOffset().getTotalSeconds();
        int now = date.toInstant().atZone(sourceZonId).getOffset().getTotalSeconds();

        return Date.from(date.toInstant().plus(target - now, ChronoUnit.SECONDS));
    }


    public static String getBrazilNowDate() {
        return getZoneDate(SP);
    }

    public static String getBrazilNowDate(String formatter) {
        return getZoneDate(SP, DateTimeFormatter.ofPattern(formatter));
    }

    public static String getUtcNowDate() {
        return getZoneDate(ZoneOffset.UTC);
    }

    public static String getUtcNowDate(String formatter) {
        return getZoneDate(ZoneOffset.UTC, DateTimeFormatter.ofPattern(formatter));
    }

    public static String getDefaultNowDate() {
        return getZoneDate(ZoneOffset.systemDefault());
    }

    public static String getDefaultNowDate(String formatter) {
        return getZoneDate(ZoneOffset.systemDefault(), DateTimeFormatter.ofPattern(formatter));
    }

    public static String getZoneDate(ZoneId zoneId) {
        return getZoneDate(zoneId, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String getZoneDate(ZoneId zoneId, String formatter) {
        return getZoneDate(zoneId, DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * 获取指定时区的时间
     *
     * @param zoneId
     * @param formatter
     * @return
     */
    public static String getZoneDate(ZoneId zoneId, DateTimeFormatter formatter) {
        return LocalDateTime.now(zoneId).format(formatter);
    }


    public static long dayDistance(Date d1, Date d2) {
        return dayDistance(d1, d2, ZoneId.systemDefault());
    }

    public static long dayDistance(Date d1, Date d2, ZoneId zoneId) {
        return datesDistance(d1, d2, zoneId, ChronoUnit.DAYS);
    }

    public static long yearDistance(Date d1, Date d2) {
        return yearDistance(d1, d2, ZoneId.systemDefault());
    }

    public static long yearDistance(Date d1, Date d2, ZoneId zoneId) {
        return datesDistance(d1, d2, zoneId, ChronoUnit.YEARS);
    }

    public static long datesDistance(Date d1, Date d2, ZoneId zoneId, ChronoUnit chronoUnit) {
        if (zoneId == null) {
            return chronoUnit.between(d1.toInstant(), d2.toInstant());
        }
        return chronoUnit.between(d1.toInstant().atZone(zoneId), d2.toInstant().atZone(zoneId));
    }

    public static int getAge(String date, String format) {
        return (int) yearDistance(dateStrToDate(date, format), new Date());
    }

    public static void main(String[] args) throws ParseException {
        Date spDate = toSPDate(new Date());
        System.out.println(spDate);
    }

    public static String getFormatedDateString(float timeZoneOffset,Date d){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        int newTime=(int)(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(newTime);
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(newTime, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(d);
    }
}
