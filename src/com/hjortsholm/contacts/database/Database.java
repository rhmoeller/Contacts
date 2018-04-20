package com.hjortsholm.contacts.database;

import com.hjortsholm.contacts.models.DataModel;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <h1>Database</h1>
 * A programming interface for interacting with SQLite
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @since 10/04/2018
 */
public class Database {

    private static final String JDBC = "jdbc:sqlite:";
    private static final int TIMEOUT = 30;
    private static Connection connection;
    private static String path;

    /**
     * Configure the database connection.
     *
     * @param path File path to database.
     */
    public static boolean configure(String path) {
        try {
            Database.path = path;
            Database.connection = DriverManager.getConnection(Database.JDBC + path);
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: Failed to open database..");
            return false;
        }
    }


    /**
     * Get information from the database.
     *
     * @param query {@link Query} to perform.
     * @return {@link QuerySet Data set} result from query.
     */
    public static QuerySet get(Query query) {
        return Database.get(query.toString());
    }

    /**
     * Get information from the database.
     *
     * @param query Query to perform.
     * @return {@link QuerySet Data set} result from query.
     */
    public static QuerySet get(String query) {
        try {
            if (connection == null) throw new Exception("No connection");
            if (connection.isClosed()) throw new Exception("Connection is closed");
            Statement statement = Database.connection.createStatement();
            statement.setQueryTimeout(Database.TIMEOUT);
            return new QuerySet(statement.executeQuery(query));
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            return null;
        } catch (Exception exception) {
            System.err.println("[ERROR]: " + exception.getMessage());
            return null;
        }
    }

    /**
     * Send data to the database.
     *
     * @param query {@link Query} to perform.
     * @return Successfully performed query.
     * @see TableField
     */
    public static boolean post(Query query) {
        return Database.post(query.toString(), new Object[0]);
    }

    /**
     * Send data to the database.
     *
     * @param query Query to perform.
     * @return Successfully performed query.
     * @see TableField
     */
    public static boolean post(String query) {
        return Database.post(query, new Object[0]);
    }

    /**
     * Send data to the database.
     *
     * @param query Query to perform.
     * @param data  Data to send.
     * @return Successfully performed query.
     * @see TableField
     */
    public static boolean post(String query, Object... data) {
        try {
            PreparedStatement statement = Database.connection.prepareStatement(query);
            statement.setQueryTimeout(Database.TIMEOUT);
            if (data != null) {
                for (int i = 0; i < data.length; i++) {
                    statement.setObject(i + 1, data[i]);
                }
            }
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException exception) {
            System.err.println("[ERROR]: " + query);
            return false;
        }
    }

    /**
     * See if database connection is closed.
     *
     * @return Closed connection.
     */
    public static boolean isClosed() {
        if (Database.connection != null) {
            try {
                return Database.connection.isClosed();
            } catch (SQLException exception) {
                System.err.println("[ERROR]: " + exception.getMessage());
            }
        }
        return false;
    }

    /**
     * Get the path to the database file.
     *
     * @return Path to database file.
     */
    public static String getPath() {
        return Database.path;
    }

    /**
     * Check if the database file exists and if the connection is valid.
     *
     * @return Valid connection.
     */
    public static boolean isValid() {
        if (Database.connection != null) {
            try {
                File databaseFile = new File(Database.getPath());
                if (databaseFile.exists() && Database.connection.isValid(30))
                    return true;
            } catch (SQLException exception) {
                System.err.println("[ERRROR]: " + exception);
            }
        }
        return false;

    }

    /**
     * Creates table based on a class.
     *
     * @param dataModel {@link DataModel Table Model} for table.
     * @see TableField Table Field
     */
    public static void createTable(Class<?> dataModel) {
        Database.createTable(dataModel.getSimpleName(), dataModel);
    }

    /**
     * Creates table based on a class.
     *
     * @param table     Table name.
     * @param dataModel {@link DataModel Table Model} for table.
     * @see TableField Table Field
     */
    private static void createTable(String table, Class<?> dataModel) {
        ArrayList<TableField> fields = new ArrayList<>();
        Collections.addAll(fields, (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class));
        StringBuilder sql = new StringBuilder("CREATE TABLE " + table + " (\n");
        for (int i = 0; i < fields.size(); i++) {
            TableField field = fields.get(i);
            sql.append(String.format("\t%s\t%s\t%s\t%s\t%s\t%s%s\n",
                    field.name(),
                    field.type(),
                    field.primaryKey() ? "PRIMARY KEY" : "",
                    field.isNullable() ? "" : "NOT NULL",
                    field.autoincrement() ? "AUTOINCREMENT" : "",
                    field.defaultValue().isEmpty() ? "" : "DEFAULT " + field.defaultValue(),
                    i + 1 < fields.size() ? "," : ""
            ));
        }
        sql.append(")");
        Database.post(sql.toString());
    }

    /**
     * Delete table from databae.
     *
     * @param dataModel {@link DataModel Table Model}.
     * @see TableField Table Field
     */
    public static void dropTable(Class<?> dataModel) {
        Database.dropTable(dataModel.getSimpleName());
    }

    /**
     * Delete table from databae.
     *
     * @param table Table name.
     */
    private static void dropTable(String table) {
        Database.post(new Query().drop(table).toString());
    }

    /**
     * Compare {@link TableField data model} to the related table in database.
     *
     * @param dataModel Data model.
     * @return Table corresponds to data model.
     * @see TableField
     */
    public static boolean verifyTable(Class<?> dataModel) {
        return Database.verifyTable(dataModel.getSimpleName(), dataModel);
    }

    /**
     * Compare {@link TableField data model} to the related table in database.
     *
     * @param table     Table name.
     * @param dataModel Data model.
     * @return Table corresponds to data model.
     * @see TableField
     */
    private static boolean verifyTable(String table, Class<?> dataModel) {
        TableField[] fields = (TableField[]) dataModel.getDeclaredAnnotationsByType(TableField.class);
        QuerySet tableFields = Database.get(new Query().getInfo(table).toString());
        QuerySet iterableFields = (QuerySet) tableFields.clone();
        ArrayList<QueryRow> verifiedField = new ArrayList<>();

        if (fields.length == 0 || fields.length != tableFields.size() || tableFields.isEmpty()) {
            return false;
        }

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

    /**
     * Insert data from a {@link DataModel data model}.
     *
     * @param model {@link DataModel Data} to insert.
     * @see DataModel
     * @see TableField
     */
    public static void insert(DataModel model) {
        Database.post(new Query().insertInto(model.getClass()).values(model.getValues()));
    }

}
