package com.hjortsholm.contacts.database;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Database {
    private static final String JDBC = "jdbc:sqlite:";
    private Connection connection;
    private int timeout = 30;

    public Database(String databasePath) {
        try {
            this.connection = DriverManager.getConnection(this.JDBC + databasePath);
        } catch (SQLException exception) {
            System.err.println("[ERROR]: Failed to open database..");
        }
    }

    public boolean get(String query, Consumer<QueryResult> success) {
        return this.get(query, success, null);
    }

    public boolean get(String query, Consumer<QueryResult> success, Consumer<SQLException> failed) {
        try {
            if (connection == null) return false;
            if (connection.isClosed()) return false;
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(this.timeout);
            ResultSet results = statement.executeQuery(query);
            if (success != null)
                while (results.next())
                    success.accept(new QueryResult(results));
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: "+query);
            if (failed != null)
                failed.accept(exception);
            return false;
        }
    }

    public boolean post(String query) {
        return this.post(query,null);
    }

    public boolean post(String query, Object... data) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(query+";");
            statement.setQueryTimeout(this.timeout);
            if(data != null)
                for (int i = 0; i < data.length; i++)
                    statement.setObject(i + 1, data[i]);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: "+query);
            return false;
        }
    }

    public boolean createTable(String table, Class dataModel) {
        ArrayList<TableField> fields = new ArrayList<>();
        for (Method method : dataModel.getDeclaredMethods()) {
            TableField annotation = method.getDeclaredAnnotationsByType(TableField.class)[0];
            if (annotation != null)
                fields.add(annotation);
        }

        String sql = "CREATE TABLE " + table + " (";
        for (int i = 0; i < fields.size(); i++) {
            TableField field = fields.get(i);
            sql += String.format("%s\t%s\t%s\t%s\t%s%s",
                    field.name(),
                    field.type(),
                    field.primaryKey() ? "PRIMARY KEY" : "",
                    field.isNullable() ? "" : "NOT NULL",
                    field.defaultValue().isEmpty() ? "" : "DEFAULT " + field.defaultValue(),
                    i + 1 > fields.size() ? "," : ""
            );
        }
        sql += ")";
        return this.post(sql);
    }

    public boolean dropTable(String table) {
        return this.post("DROP TABLE "+table);
    }
}
