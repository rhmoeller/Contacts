package com.hjortsholm.contacts.util;

import com.hjortsholm.contacts.Application;

import java.io.File;

public class Resource extends File {
    public Resource(String pathname) {
        super(Application.class.getResource(pathname).toString());
    }
}
