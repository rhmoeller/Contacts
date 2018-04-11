package com.hjortsholm.contacts.database;

public enum Query {
    NAME("SELECT * FROM Field WHERE contact = 0");




    private String queryString;

    Query(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return this.queryString;
    }
}
