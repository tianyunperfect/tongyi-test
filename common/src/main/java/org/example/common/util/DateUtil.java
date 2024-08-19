package org.example.common.util;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 日期大小比较
 * 日期加减
 * 时间戳转换
 *
 * @author tianyunperfect
 * @date 2020/05/28
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    // 获取当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
    public static String getCurrentDateTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(new Date());
    }

    public static Date datePlusDay(Date date,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    public static Date datePlusDay(String dayStr,int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date date = sdf.parse(dayStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            return calendar.getTime();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 当前毫秒
     *
     * @return {@link Long}
     */
    public static Long getEpochMilli() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 当前秒
     *
     * @return {@link Long}
     */
    public static Long getEpochSecond() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd HH:mm:ss 转 毫秒
     */
    public static String convertMilliToDateStr(Long time) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
    }

    /**
     * 将字符串转日期成Long类型的时间戳，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static Long convertDateStrToMilli(String time) {
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2018-05-29 13:52:50", ftf);
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取当前日期字符串的方法，例如 2022-03-03
     */
    public static String getCurrentDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(new Date());
    }

    /**
     * 字符串日期 加减天数
     *
     * @param dateStr 日期str
     * @param days    天
     * @return {@link String}
     */
    public static String getDateStrPlusDays(String dateStr, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取第二天日期字符串的方法，例如 2022-03-04
     */
    public static String getNextDayDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        return sdf.format(getNextDayDate());
    }
    public static Date getNextDayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
    public static String getPreMonthDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return sdf.format(calendar.getTime());
    }
    public static void main(String[] args) {
        System.out.println(getNextDayDateStr());
        System.out.println(getNextDayDate());
    }

    public static boolean isBetween(String activeDay, String fromDay, String endDay) {
        return activeDay != null && activeDay.compareTo(fromDay) >= 0 && activeDay.compareTo(endDay) <= 0;
    }
}
