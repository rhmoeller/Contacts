package com.hjortsholm.contacts.database;

import java.lang.annotation.*;

//@Target(ElementType.FIELD)
@Repeatable(TableFields.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String name();

    String type();

    boolean primaryKey() default false;

    boolean isNullable() default true;

    String defaultValue() default "";
}
