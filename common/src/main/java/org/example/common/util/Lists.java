package org.example.common.util;

import java.util.ArrayList;

public class Lists {
    public static <T> ArrayList<T> of(T... elements) {
        ArrayList<T> list = new ArrayList<>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
}
