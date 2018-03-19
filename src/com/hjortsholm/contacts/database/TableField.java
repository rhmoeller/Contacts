package com.hjortsholm.contacts.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableField {
    String name();

    String type();

    boolean primaryKey() default false;

    boolean isNullable() default true;

    String defaultValue() default "";

}
