package com.hjortsholm.contacts.database;

/**
 * <h1>Query</h1>
 * A query builder that enforces correct syntax.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see Database
 * @since 10/04/2018
 */
public class Query {

    private String query;

    /**
     * Initialise variables.
     */
    public Query() {
        this.query = "";
    }

    /**
     * Append object(s) to query string.
     *
     * @param objects Array of objects to append
     * @return Instance of self.
     */
    private Query append(Object... objects) {
        return this.append(false, objects);
    }

    /**
     * Append object(s) to query string separated by commas.
     *
     * @param commaSeparated Separate objects by commas.
     * @param objects        Array of objects to append
     * @return Instance of self.
     */
    private Query append(boolean commaSeparated, Object[] objects) {
        return this.append(commaSeparated, false, objects);
    }

    /**
     * Append object(s) to query string separated by commas.
     *
     * @param commaSeparated Separate objects by commas.
     * @param escaped        Strings are escaped.
     * @param objects        Array of objects to append
     * @return Instance of self.
     */
    private Query append(boolean commaSeparated, boolean escaped, Object... objects) {
        for (int i = 0; i < objects.length; i++)
            query += (objects[i].getClass().getSimpleName().equals(String.class.getSimpleName()) && escaped ? "\"" + objects[i]/*.toString()*/ + "\"" : objects[i].toString()).replaceAll(";", "") + (commaSeparated && i + 1 < objects.length ? ", " : " ");
        return this;
    }

    /**
     * Create query string to drop a table.
     *
     * @param table The data model of a table.
     * @return Instance of self.
     */
    public Query drop(Class table) {
        return this.drop(table.getSimpleName());
    }

    /**
     * Create query string to drop a table.
     *
     * @param table The table name.
     * @return Instance of self.
     */
    public Query drop(String table) {
        this.query = "DROP TABLE " + table;
        return this;
    }

    /**
     * Create query string to get information about a table.
     *
     * @param table The data model of a table.
     * @return Instance of self.
     */
    public Query getInfo(Class table) {
        return this.getInfo(table.getSimpleName());
    }

    /**
     * Create query string to get information about a table.
     *
     * @param table The table name.
     * @return Instance of self.
     */
    public Query getInfo(String table) {
        this.query = "PRAGMA table_info(" + table + ")";
        return this;
    }

    /**
     * Create query string to delete a row in a table.
     *
     * @param table The data model of a table.
     * @return Instance of self.
     */
    public Query deleteFrom(Class table) {
        return this.deleteFrom(table);
    }

    /**
     * Create query string to delete a row in a table.
     *
     * @param table The data model of a table.
     * @return Instance of self.
     */
    public Query deleteFrom(String table) {
        return this.append("DELETE FROM", table);
    }

    /**
     * Create query string to update a row in a table.
     *
     * @param table The table name.
     * @return Instance of self.
     */
    public Query update(String table) {
        return this.append("UPDATE ", table);
    }

    /**
     * Appends to query string to set fields to values in a UPDATE statement table.
     *
     * @param values The values to modify
     * @return Instance of self.
     */
    public Query set(String... values) {
        this.append("SET");
        return this.append(true, values);
    }

    /**
     * Create query string to insert a row in a table.
     *
     * @param table The data model of a table.
     * @return Instance of self.
     */
    public Query insertInto(Class table) {
        return this.insertInto(table.getSimpleName());
    }

    /**
     * Create query string to insert a row in a table.
     *
     * @param table The table name.
     * @return Instance of self.
     */
    public Query insertInto(String table) {
        return this.append("INSERT INTO", table);
    }

    /**
     * Appends to query string to pick what fields to insert in a table.
     *
     * @param fields The you want to insert into a table.
     * @return Instance of self.
     */
    public Query fields(String... fields) {
        this.append("(");
        this.append(true, fields);
        return this.append(")");
    }

    /**
     * Appends to query string to say what values you want to insert in a table.
     *
     * @param values The values you want to insert.
     * @return Instance of self.
     */
    public Query values(Object... values) {
        this.append("VALUES (");
        this.append(true, true, values);
        return this.append(")");
    }

    /**
     * Appends to query string to insert default values in all fields in a table.
     *
     * @return Instance of self.
     */
    public Query defaultValues() {
        return this.append("DEFAULT VALUES");
    }

    /**
     * Creates a query string that selects all fields.
     *
     * @return Instance of self.
     */
    public Query select() {
        return this.select("*");
    }

    /**
     * Creates a query string to selects specific fields.
     *
     * @param what What specific fields to select.
     * @return Instance of self.
     */
    public Query select(String what) {
        return this.append("SELECT", what);
    }

    /**
     * Appends to query string to specify what tables to perform statement in.
     *
     * @param tables Tables in which to perform statement.
     * @return Instance of self.
     */
    public Query from(String tables) {
        return this.append("FROM", tables);
    }

    /**
     * Appends to query string to specify what tables to perform statement in.
     *
     * @param table The data model's table in which to perform statement.
     * @return Instance of self.
     */
    public Query from(Class table) {
        return this.from(table.getSimpleName());
    }

    /**
     * Appends to query string what condition to apply to query.
     *
     * @param condition Condition to apply to query.
     * @return Instance of self.
     */
    public Query where(String condition) {
        return this.append("WHERE", condition);
    }

    /**
     * Appends another condition to the query string.
     *
     * @param condition Condition to apply to query.
     * @return Instance of self.
     */
    public Query and(String condition) {
        return this.append("AND", condition);
    }

    /**
     * Appends to query string what conditions to apply to query.
     *
     * @param conditions Conditions to apply to query.
     * @return Instance of self.
     */
    public Query where(String... conditions) {
        this.where(conditions[0]);
        for (int i = 1; i < conditions.length; i++)
            this.and(conditions[i]);
        return this;
    }

    /**
     * Gets the query string.
     *
     * @return Query string.
     */
    @Override
    public String toString() {
        return this.query;
    }
}
