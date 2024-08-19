package org.example.common.util;

import java.util.List;

public class MyErrorUtil {

    public static void isTrue(Boolean bool, String msg) {
        if (!bool) {
            throw new BusinessException(msg);
        }
    }

    public static void throwE(String msg) {
        throw new BusinessException(msg);
    }
    public static void check(Object object, String msg) {
        if (object instanceof List) {
            List<?> list = (List<?>) object;
            if (list.isEmpty()) {
                throwE(msg);
            }
        } else {
            if (object == null) {
                throwE(msg);
            }
        }
    }
    // 自定义一个异常

    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }

}
