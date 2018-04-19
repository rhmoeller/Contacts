package com.hjortsholm.contacts.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * <h1>Query Row</h1>
 * An object map for database table rows.
 *
 * @author robertmoller s5069583
 * @version 1.0
 * @see QuerySet
 * @since 10/04/2018
 */
public class QueryRow {
    private HashMap<String, Object> columns;

    /**
     * Stores all results from a {@link ResultSet result set} in a {@link HashMap hash map}with it's original column name.
     *
     * @param results Query result.
     */
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

    /**
     * Gets a specific column from the previously created {@link HashMap hash map} by column name.
     *
     * @param key Column name.
     * @return Object from the query result data.
     */
    public Object getColumn(String key) {
        return this.columns.keySet().contains(key) ? this.columns.get(key) : null;
    }
}
