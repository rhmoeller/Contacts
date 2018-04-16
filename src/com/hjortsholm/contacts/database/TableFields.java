package com.hjortsholm.contacts.database;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <h1>Table Fields</h1>
 * Needed annotation interface to allow multiple {@link TableField}'s
 * for one class
 *
 * @author robertmoller s5069583
 * @since 10/04/2018
 * @version 1.0
 * @see TableField
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TableFields {
    TableField[] value();
}