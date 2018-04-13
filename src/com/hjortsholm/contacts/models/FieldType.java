package com.hjortsholm.contacts.models;

import com.hjortsholm.contacts.database.TableField;
import com.hjortsholm.contacts.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@TableField(name = "id", type = "INTEGER", primaryKey = true)
//@TableField(name = "name", type = "VARCHAR", isNullable = false)
//@TableField(name = "prompt", type = "VARCHAR", isNullable = false)
public enum FieldType {
    NUMBER("mobile"),
    EMAIL("personal"),
    NAME("first"),
    DATE("birthday"),
    ADDRESS("home"),
    NOTE("note");

    private static int defaultIndex = 0;
    private static Map map = new HashMap<>();
    private int index;
    private String defaultName;

    FieldType(String defaultName) {
        this.index = getNewIndex();
        this.defaultName = defaultName;
    }

    protected static int getNewIndex() {
        return defaultIndex++;
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public int getIndex() {
        return this.index;
    }

    static {
        for (FieldType FieldType : FieldType.values()) {
            map.put(FieldType.index, FieldType);
        }
    }

    public static FieldType valueOf(int fieldType) {
        return (FieldType) map.get(fieldType);
    }

}
