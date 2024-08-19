package org.example.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author mlamp
 * @date 2022/05/21
 */
public class StringUtil {
    /**
     * 是否为空白字符串，比较常用
     *
     * @param s 年代
     * @return {@link Boolean}
     */
    public static Boolean isBlank(String s) {
        if (s != null && s.replaceAll("\\s*", "").length() > 0) {
            return false;
        }
        return true;
    }

    public static boolean isNotBlank(String... strings) {
        for (String s : strings) {
            if (isBlank(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNotNull(Object... objects) {
        for (Object s : objects) {
            if (s == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllTrue(Boolean... booleans) {
        for (Boolean s : booleans) {
            if (!s) {
                return false;
            }
        }
        return true;
    }

    public static void ifAllNotBlank(Runnable function, String... str) {
        if (isNotBlank(str)) {
            function.run();
        }
    }
    public static void ifAnyBlank(Runnable function, String... str) {
        if (!isNotBlank(str)) {
            function.run();
        }
    }

    public static void ifTrue(Boolean b,Runnable function) {
        if (isAllTrue(b)) {
            function.run();
        }
    }

    public static void ifAllNotNull(Runnable function, Object... obj) {
        if (isAllNotNull(obj)) {
            function.run();
        }
    }

    public static Boolean isEmpty(String s) {
        if (s != null && s.length() > 0) {
            return false;
        }
        return true;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");

    public static boolean isNumber(String str) {
        if (str == null) {
            return false;
        }
        // 通过Matcher进行字符串匹配
        Matcher m = pattern.matcher(str);
        // 如果正则匹配通过 m.matches() 方法返回 true ，反之 false
        return m.matches();
    }

    /**
     * 小数,百分比
     *
     * @param decimalStr 十进制str
     * @param scale      规模
     * @return {@link String}
     */
    public static String decimalToPercentage(String decimalStr, int scale) {
        try {
            BigDecimal decimal = new BigDecimal(decimalStr);
            BigDecimal bd = decimal.multiply(new BigDecimal(100));
            bd = bd.setScale(scale, RoundingMode.HALF_UP);
            return bd.doubleValue() + "%";
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String divideAndRound(long num1, long num2) {
        if (num2 == 0) {
            throw new RuntimeException("除数不能为0");
        }
        double result = (double) num1 / num2;
        return String.format("%.2f", result * 100) + "%";
    }

    public static String getRandomNumber(int i) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < i; j++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static final String PHONE_NUMBER_PATTERN = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";

    public static boolean validatePhoneNumber(String phoneNumber) {
        // 手机号正则表达式
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }

    // 忽略字符串大小，比较是否相等
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static List<String> getImgSrc(String htmlText) {
        List<String> urls = new ArrayList<>();
        // 定义URL匹配模式
        String urlPattern = "<img.*?src=[\"'](.*?)[\"'].*?>";
        // 创建Pattern对象
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        // 创建Matcher对象
        Matcher matcher = pattern.matcher(htmlText);
        // 匹配所有URL地址
        while (matcher.find()) {
            String url = matcher.group(1);
            urls.add(url);
        }
        return urls;
    }
    // 方法：去除 htmlText 中的 图片地址
    public static String removeImgSrc(String htmlText) {
        // 定义URL匹配模式
        String urlPattern =  "<img.*?src=[\"'](.*?)[\"'].*?>";
        // 创建Pattern对象
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        // 创建Matcher对象
        Matcher matcher = pattern.matcher(htmlText);
        // 替换所有URL地址
        return matcher.replaceAll("");
    }
    // 获取html中text文本内容
    public static String getHtmlText(String htmlText) {
        // 定义text匹配模式
        String textPattern = "<[^>]+>";
        // 创建Pattern对象
        Pattern pattern = Pattern.compile(textPattern, Pattern.CASE_INSENSITIVE);
        // 创建Matcher对象
        Matcher matcher = pattern.matcher(htmlText);
        // 匹配所有text内容
        return matcher.replaceAll("");
    }
    public static void main(String[] args) {
        String htmlText = """
                {"front":"<p>1茶<img src=\\"https://img.yizhizs.cn/19f573a06577dc6b6fe91052944889c7.jpg\\" alt=\\"图像\\"></p>","back":{"type":"MULTIPLE_CHOICE","QUESTION_ANSWER":{"value":""},"MULTIPLE_CHOICE":{"values":[{"value":"绿茶","checked":true},{"value":"红茶","checked":false},{"value":"黑茶","checked":false},{"value":"","checked":false}]},"TRUE_FALSE":{"value":""}}}
                """;
        System.out.println(getImgSrc(htmlText));
        System.out.println(removeImgSrc(htmlText));
    }
}
