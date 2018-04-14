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

    private static Connection connection;
    private static String path;
    private static int timeout = 30;

    public static boolean configure(String databasePath) {
        try {
            Database.path = databasePath;
            Database.connection = DriverManager.getConnection(Database.JDBC + databasePath);
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: Failed to open database..");
            return false;
        }
    }

    public static QuerySet get(String query) {
        try {
            if (connection == null) throw new Exception("No connection");
            if (connection.isClosed()) throw new Exception("Connection is closed");
            Statement statement = Database.connection.createStatement();
            statement.setQueryTimeout(Database.timeout);
            return new QuerySet(statement.executeQuery(query));
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            return null;
        } catch (Exception exception) {
            System.err.println("[ERROR]: " + exception.getMessage());
            return null;
        }
    }

    public static boolean post(String query) {
        return Database.post(query,null);
    }

    public static boolean post(String query, Object... data) {
        try {
            PreparedStatement statement = Database.connection.prepareStatement(query/*+";"*/);
            statement.setQueryTimeout(Database.timeout);
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

    public boolean close() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + exception);
        }
        return false;
    }

    public static boolean isClosed() {
        try {
            return Database.connection.isClosed();
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

//    public boolean get(String query, Consumer<QueryRow> success) {
//        return this.get(query, success, null);
//    }
//
//    public boolean get(String query, Consumer<QueryRow> success, Consumer<SQLException> failed) {
//        try {
//            if (connection == null) return false;
//            if (connection.isClosed()) return false;
//            Statement statement = this.connection.createStatement();
//            statement.setQueryTimeout(this.timeout);
//            ResultSet results = statement.executeQuery(query);
//            if (success != null)
//                while (results.next())
//                    success.accept(new QueryRow(results));
//            return true;
//        } catch (SQLException exception) {
//            System.err.println("[ERROR]: " + query);
//            if (failed != null)
//                failed.accept(exception);
//            return false;
//        }
//    }

//    public boolean post(String query) {
//        return this.post(query, null);
//    }

//    public boolean post(String query, Object... data) {
//        try {
//            PreparedStatement statement = this.connection.prepareStatement(query/*+";"*/);
//            statement.setQueryTimeout(this.timeout);
//            if (data != null)
//                for (int i = 0; i < data.length; i++)
//                    statement.setObject(i + 1, data[i]);
//            statement.executeUpdate();
//            statement.close();
//            return true;
//        } catch (SQLException exception) {
//            System.err.println("[ERROR]: " + query);
//            return false;
//        }
//    }

    public static boolean createTable(Class dataModel) {
        return Database.createTable(dataModel.getSimpleName(), dataModel);
    }

    public static boolean createTable(String table, Class dataModel) {
        ArrayList<TableField> fields = new ArrayList<>();
        for (TableField field : (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class))
            fields.add(field);


        String sql = "CREATE TABLE " + table + " (\n";
        for (int i = 0; i < fields.size(); i++) {
            TableField field = fields.get(i);
            sql += String.format("\t%s\t%s\t%s\t%s\t%s\t%s%s\n",
                    field.name(),
                    field.type(),
                    field.primaryKey() ? "PRIMARY KEY" : "",
                    field.isNullable() ? "" : "NOT NULL",
                    field.autoincrement() ? "AUTOINCREMENT" : "",
                    field.defaultValue().isEmpty() ? "" : "DEFAULT " + field.defaultValue(),
                    i + 1 < fields.size() ? "," : ""
            );
        }
        sql += ")";
        return Database.post(sql);
    }

    public static boolean dropTable(Class dataModel) {
        return Database.dropTable(dataModel.getSimpleName());
    }

    public static boolean dropTable(String table) {
        return Database.post("DROP TABLE " + table);
    }

    public static boolean verifyTable(Class dataModel) {
        return Database.verifyTable(dataModel.getSimpleName(), dataModel);
    }

    public static boolean verifyTable(String table, Class dataModel) {
        TableField[] fields = (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class);
        ArrayList<QueryRow> tableFields = Database.get("PRAGMA table_info(" + table + ")");
        ArrayList<QueryRow> iterableFields = (ArrayList<QueryRow>) tableFields.clone();
        ArrayList<QueryRow> verifiedField = new ArrayList<>();

        if (fields.length == 0 || /**/fields.length != tableFields.size()/**/ || tableFields.isEmpty()) {
            return false;
        }

//        iterableFields =
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
