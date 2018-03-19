package com.hjortsholm.contacts;

public class Resource {
    public static String get(String relativePath) {
        return Resource.class.getResource(relativePath).toString();
    }
}
