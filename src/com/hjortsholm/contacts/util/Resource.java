package com.hjortsholm.contacts.util;

import com.hjortsholm.contacts.Application;

import java.io.File;

/**
 * <h1>Resouce</h1>
 * Extends {@link File file class} to accept relative resource paths.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class Resource extends File {
    public Resource(String pathname) {
        super(Application.class.getResource(pathname).toString());
    }
}
