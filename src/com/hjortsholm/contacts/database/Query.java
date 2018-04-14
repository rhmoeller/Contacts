package com.hjortsholm.contacts.database;

public class Query {

    private String query;

    public Query() {
        this.query = "";
    }

    private Query append(Object... objects) {
        return this.append(false, objects);
    }

    private Query append(boolean commaSeperated, Object... objects) {
        return this.append(commaSeperated,false,objects);
    }

    private Query append(boolean commaSeperated, boolean escaped, Object... objects) {
        for (int i = 0; i < objects.length; i++)
            query += ( objects[i].getClass().getSimpleName().equals(String.class.getSimpleName())  && escaped ? "\"" + objects[i]/*.toString()*/ + "\"" : objects[i].toString()).replaceAll(";", "") + (commaSeperated && i+1 < objects.length  ? ", " : " ");
        return this;
    }

    public Query drop(String table) {
        this.query = "DROP TABLE "+ table;
        return this;
    }

    public Query getInfo(String table) {
        this.query = "PRAGMA table_info(" + table+")";
        return this;
    }


    public Query deleteFrom(String table) {
        return this.append("DELETE FROM",table);
    }

    public Query update(String table) {
        return this.append("UPDATE ",table);
    }

    public Query set(String... values) {
        this.append("SET");
        return this.append(true, values);
    }

    public Query insertInto(String table) {
        return this.append("INSERT INTO",table);
    }

    public Query fields(String... fields) {
        this.append("(");
        this.append(true,fields);
        return this.append(")");
    }

    public Query values(Object... values) {
        this.append("VALUES (");
        this.append(true, true, values);
        return this.append(")");
    }

    public Query defaultValues() {
        return this.append("DEFAULT VALUES");
    }

    public Query select() {
        return this.select("*");
    }


    public Query select(String what) {
        return this.append("SELECT", what);
    }

    public Query from(String tables) {
        return this.append("FROM", tables);
    }

    public Query from(Class table) {
        return this.from(table.getSimpleName());
    }

    public Query where(String condition) {
        return this.append("WHERE", condition);
    }

    public Query and(String condition) {
        return this.append("AND", condition);
    }

    public Query or(String condition) {
        return this.append("OR", condition);
    }
//
//    public Query and(Me<Query, Query> condition) {
//        return this.nested(condition);
//    }

    public Query where(String... conditions) {
        this.where(conditions[0]);
        for (int i = 1; i < conditions.length; i++)
            this.and(conditions[i]);
        return this;
    }

//    public Query nested(Me<Query, Query> nestedQuery) {
//        this.append("(");
//        nestedQuery.accept(this);
//        return this.append(")");
//    }

    @Override
    public String toString() {
//        System.out.println(this.query);
        return this.query;
    }
}
