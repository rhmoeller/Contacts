package com.hjortsholm.contacts.models;

public enum FieldType {
    NUMBER("mobile"),
    EMAIL("personal"),
    NAME("first"),
    DATE("birthday"),
    ADDRESS("home"),
    NOTE("note");

    private static int defaultIndex = 0;
    private int index;
    private String defaultName;

    FieldType(String defaultName) {
        this.index = getNewIndex();
        this.defaultName = defaultName;
    }

    protected static int getNewIndex() {
        return defaultIndex++;
    }

    public static FieldType valueOf(int fieldType) {
        return FieldType.values()[fieldType];
    }

    public String getDefaultName() {
        return this.defaultName;
    }

    public int getIndex() {
        return this.index;
    }

}
