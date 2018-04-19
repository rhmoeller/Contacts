package com.hjortsholm.contacts.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <h1>Query Set</h1>
 * An object map for database table.
 *
 * @author Robert Moeller s5069583
 * @version 1.0
 * @see QueryRow
 * @since 10/04/2018
 */
public class QuerySet extends ArrayList<QueryRow> {

    /**
     * Creates a list of {@link QueryRow query rows} from the table returned by a query.
     *
     * @param rows Query result.
     * @throws SQLException Result set error.
     */
    public QuerySet(ResultSet rows) throws SQLException {
        while (rows.next())
            this.add(new QueryRow(rows));
    }

    /**
     * Get the first row from the table.
     *
     * @return {@link QueryRow Row} from table.
     */
    public QueryRow first() {
        return this.size() > 0 ? this.get(0) : null;
    }

    /**
     * Get the last row from the table.
     *
     * @return {@link QueryRow Row} from table.
     */
    public QueryRow last() {
        return this.get(this.size() - 1);
    }
}
