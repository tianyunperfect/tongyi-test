package org.example.common.util;

import java.util.HashMap;

public class Maps {

    public static <K, V> HashMap<K, V> of(K key, V value, Object... more) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, value);
        for (int i = 0; i < more.length; i += 2) {
            map.put((K) more[i], (V) more[i + 1]);
        }
        return map;
    }

}
