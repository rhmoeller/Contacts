package com.hjortsholm.contacts.util;

public class StringUtils {
    public static String formalise(String string) {
        if (string.length() > 1)
            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        else
            return string.toUpperCase();
    }
}
