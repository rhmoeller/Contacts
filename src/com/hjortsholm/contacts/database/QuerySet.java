package com.hjortsholm.contacts.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class QuerySet extends ArrayList<QueryRow> {

    public QuerySet(ResultSet rows) throws SQLException {
        while (rows.next())
            this.add(new QueryRow(rows));
    }

//    public QuerySet(ArrayList<QueryRow> rows) {
//        this.addAll(rows);
//    }

    public QueryRow first() {
        return this.get(0);
    }

    public QueryRow last() {
        return this.get(this.size()-1);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()+super.toString();
    }
}
