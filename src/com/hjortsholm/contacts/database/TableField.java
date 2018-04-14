package com.hjortsholm.contacts.database;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target(ElementType.FIELD)
@Repeatable(TableFields.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String name();

    String type();

    boolean primaryKey() default false;

    boolean isNullable() default true;

    boolean autoincrement() default false;

    String defaultValue() default "";
}
