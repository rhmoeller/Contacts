package com.hjortsholm.contacts.database;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <h1>Table Field</h1>
 * A annotation tag for defining table fields for a class
 * used to automate table creation, verification
 * and insertion of objects with this annotation.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @since 10/04/2018
 */
@Repeatable(TableFields.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {

    /**
     * Get the name of the column.
     *
     * @return Column name.
     */
    String name();

    /**
     * Get the data type of the column.
     *
     * @return Column data type.
     */
    String type();

    /**
     * Get weather or not the column is the primary key.
     *
     * @return Is primary key.
     */
    boolean primaryKey() default false;

    /**
     * Get weather or not the column is nullable.
     *
     * @return Is nullable.
     */
    boolean isNullable() default true;

    /**
     * Get weather or not the column auto increments.
     *
     * @return Is auto incrementing.
     */
    boolean autoincrement() default false;

    /**
     * Get the default value of the column.
     *
     * @return Column name
     */
    String defaultValue() default "";
}
