package com.aisw.community.component.util;

public class KeyCreatorBean {

    public static Object createKey(Object o1, Object o2) {
        return o1 + ":" + o2;
    }

    public static Object createKey(Object o1, Object o2, Object o3) {
        return o1 + ":" + o2 + ":" + o3;
    }
}
