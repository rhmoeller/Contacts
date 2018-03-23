package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;


@TableField(name="id", type="INTEGER", primaryKey = true)
@TableField(name="name", type="VARCHAR", isNullable = false)
public enum FieldType {

    NUMBER,
    EMAIL,
    NAME,
    DATE,
    ADDRESS,
    NOTE
}
