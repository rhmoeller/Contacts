package com.hjortsholm.contacts.models;

/**
 * <h1>Field Type</h1>
 * A enum that specifies the avaliable types of fields
 * that can be used in this application.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public enum FieldType {
    NUMBER("mobile"),
    EMAIL("personal"),
    NAME("first"),
    DATE("birthday"),
    ADDRESS("home"),
    NOTE("note"),
    PICTURE("profile");

    private static int defaultIndex = 0;
    private int index;
    private String defaultName;

    /**
     * Creates a new field type with a specified default field name.
     *
     * @param defaultName Field's default name.
     * @see Field
     */
    FieldType(String defaultName) {
        this.index = getNewIndex();
        this.defaultName = defaultName;
    }

    /**
     * Automaticly gets a unused id for the field type.
     *
     * @return Field type's id.
     */
    protected static int getNewIndex() {
        return defaultIndex++;
    }

    /**
     * Gets the field type with a specific id.
     *
     * @param fieldType Id of field type.
     * @return Field type with corresponding id.
     */
    public static FieldType valueOf(int fieldType) {
        return FieldType.values()[fieldType];
    }

    /**
     * Gets the default name of this field type.
     *
     * @return Default name for field type.
     */
    public String getDefaultName() {
        return this.defaultName;
    }

    /**
     * @return Index of field type.
     */
    public int getIndex() {
        return this.index;
    }

}
