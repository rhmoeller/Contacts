package com.hjortsholm.contacts.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

public class QueryRow {
    private HashMap<String, Object> columns;

    public QueryRow(ResultSet results) {
        this.columns = new HashMap<>();
        try {
            ResultSetMetaData metadata = results.getMetaData();
            for (int i = 1; i <= metadata.getColumnCount(); i++)
                this.columns.put(metadata.getColumnName(i), results.getObject(i));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Object getColumn(String key) {
        return this.columns.keySet().contains(key) ? this.columns.get(key): null;
    }

    public Collection<Object> getColumns() {
        return this.columns.values();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + this.columns;
    }
}
