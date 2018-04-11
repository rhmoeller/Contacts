package com.hjortsholm.contacts.database;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Database {
    private static final String JDBC = "jdbc:sqlite:";
    //    public CompletableFuture<QuerySet> getFuture(Query query) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                if (connection == null) throw new Exception("No connection");
//                if (connection.isClosed()) throw new Exception("Connection is closed");
//                Statement statement = this.connection.createStatement();
//                statement.setQueryTimeout(this.timeout);
//                return new QuerySet(statement.executeQuery(query.toString()));
//            } catch (SQLException exception) {
//                System.err.println("[ERROR]: " + query);
//                return null;
//            } catch (Exception exception) {
//                System.err.println("[ERROR]: " + exception.getMessage());
//                return null;
//            }
//        });
//    }
    private static Connection conn;
    private static String pa;
    private Connection connection;
    private String path;
    private int timeout = 30;

    public Database(String databasePath) {
        try {
            this.path = databasePath;
            this.connection = DriverManager.getConnection(this.JDBC + databasePath);
        } catch (SQLException exception) {
            System.err.println("[ERROR]: Failed to open database..");
        }
    }

    public QuerySet get(String query) {
        try {
            if (connection == null) throw new Exception("No connection");
            if (connection.isClosed()) throw new Exception("Connection is closed");
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(this.timeout);
            return new QuerySet(statement.executeQuery(query.toString()));
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            return null;
        } catch (Exception exception) {
            System.err.println("[ERROR]: " + exception.getMessage());
            return null;
        }
    }

    public boolean close() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + exception);
        }
        return false;
    }

    public boolean isClosed() {
        try {
            return this.connection.isClosed();
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + exception.getMessage());
        }
        return false;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isValid() {
        try {
            File databaseFile = new File(this.path);
            if (databaseFile.exists() && this.connection.isValid(30))
                return true;
        } catch (SQLException exception) {
            System.err.println("[ERRROR]: " + exception);
        }
        return false;

    }

    public boolean get(String query, Consumer<QueryRow> success) {
        return this.get(query, success, null);
    }

    public boolean get(String query, Consumer<QueryRow> success, Consumer<SQLException> failed) {
        try {
            if (connection == null) return false;
            if (connection.isClosed()) return false;
            Statement statement = this.connection.createStatement();
            statement.setQueryTimeout(this.timeout);
            ResultSet results = statement.executeQuery(query);
            if (success != null)
                while (results.next())
                    success.accept(new QueryRow(results));
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            if (failed != null)
                failed.accept(exception);
            return false;
        }
    }

    public boolean post(String query) {
        return this.post(query, null);
    }

    public boolean post(String query, Object... data) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(query/*+";"*/);
            statement.setQueryTimeout(this.timeout);
            if (data != null)
                for (int i = 0; i < data.length; i++)
                    statement.setObject(i + 1, data[i]);
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            return false;
        }
    }

    public boolean createTable(Class dataModel) {
        return this.createTable(dataModel.getSimpleName(), dataModel);
    }

    public boolean createTable(String table, Class dataModel) {
        ArrayList<TableField> fields = new ArrayList<>();
        for (TableField field : (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class))
            fields.add(field);


        String sql = "CREATE TABLE " + table + " (\n";
        for (int i = 0; i < fields.size(); i++) {
            TableField field = fields.get(i);
            sql += String.format("\t%s\t%s\t%s\t%s\t%s%s\n",
                    field.name(),
                    field.type(),
                    field.primaryKey() ? "PRIMARY KEY" : "",
                    field.isNullable() ? "" : "NOT NULL",
                    field.defaultValue().isEmpty() ? "" : "DEFAULT " + field.defaultValue(),
                    i + 1 < fields.size() ? "," : ""
            );
        }
        sql += ")";
        return this.post(sql);
    }

    public boolean dropTable(Class dataModel) {
        return this.dropTable(dataModel.getSimpleName());
    }

    public boolean dropTable(String table) {
        return this.post("DROP TABLE " + table);
    }

    public boolean verifyTable(Class dataModel) {
        return this.verifyTable(dataModel.getSimpleName(), dataModel);
    }

    public boolean verifyTable(String table, Class dataModel) {
        TableField[] fields = (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class);
        ArrayList<QueryRow> tableFields = new ArrayList<>();
        ArrayList<QueryRow> verifiedField = new ArrayList<>();
        ArrayList<QueryRow> iterableFields;


        this.get("PRAGMA table_info(" + table + ");", tableFields::add);
        if (fields.length == 0 || /**/fields.length != tableFields.size()/**/ || tableFields.isEmpty()) {
            return false;
        }

        iterableFields = (ArrayList<QueryRow>) tableFields.clone();
        for (TableField modelField : fields) {
            for (QueryRow tableField : iterableFields) {
                if (modelField.type().equalsIgnoreCase((String) tableField.getColumn("type")) &&
                        modelField.name().equalsIgnoreCase((String) tableField.getColumn("name")) &&
                        modelField.primaryKey() == ((int) tableField.getColumn("pk") != 0) &&
                        modelField.isNullable() == ((int) tableField.getColumn("notnull") != 1)) {
                    verifiedField.add(tableField);
                    iterableFields.remove(tableField);
                    break;
                }
            }


        }

        return tableFields.equals(verifiedField);
    }

}
