package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;


@TableField(name="id", type="INTEGER", primaryKey = true)
@TableField(name="name", type="VARCHAR", isNullable = false)
@TableField(name="prompt", type="VARCHAR", isNullable = false)
public enum FieldType {
    NUMBER,
    EMAIL,
    NAME,
    DATE,
    ADDRESS,
    NOTE;

    FieldType() {
        this.index = FieldType.getNewIndex();
    }

    private static int defaultIndex = 0;
    public static int getNewIndex() {
        return defaultIndex++;
    }

    private int index;

    public int getIndex() {
        return index;
    }
}
