package com.aetrion.activerecord;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * SQL tools.
 *
 * @author Anthony Eden
 */
public class SQL {

    private SQL() {

    }

    /**
     * Close the specified connection.
     * @param conn The JDBC connection
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // error closing, ignore it
            }
        }
    }
}
