package com.aetrion.activerecord.adapter.mysql;

import com.aetrion.activerecord.SQL;
import com.aetrion.activerecord.adapter.*;
import com.aetrion.activerecord.errors.ActiveRecordException;
import com.aetrion.activerecord.errors.StatementInvalidException;
import com.aetrion.activesupport.Strings;

import java.sql.*;
import java.util.*;

/**
 * MySQL adapter implementation.
 * @author Anthony Eden
 */
public class MySqlAdapter extends Adapter {

    /** Set to false to disable boolean emulation */
    public static boolean EMULATE_BOOLEANS = true;

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    static Map<ColumnType, NativeType> nativeDatabaseTypes = new HashMap<ColumnType, NativeType>();

    static {
        nativeDatabaseTypes.put(ColumnType.PRIMARY_KEY,
                new NativeType("int(11) DEFAULT NULL auto_increment PRIMARY KEY"));
        nativeDatabaseTypes.put(ColumnType.STRING, new NativeType("varchar", 255));
        nativeDatabaseTypes.put(ColumnType.TEXT, new NativeType("text"));
        nativeDatabaseTypes.put(ColumnType.INTEGER, new NativeType("int", 11));
        nativeDatabaseTypes.put(ColumnType.FLOAT, new NativeType("float"));
        nativeDatabaseTypes.put(ColumnType.DECIMAL, new NativeType("decimal"));
        nativeDatabaseTypes.put(ColumnType.DATETIME, new NativeType("datetime"));
        nativeDatabaseTypes.put(ColumnType.TIMESTAMP, new NativeType("datetime"));
        nativeDatabaseTypes.put(ColumnType.TIME, new NativeType("time"));
        nativeDatabaseTypes.put(ColumnType.DATE, new NativeType("date"));
        nativeDatabaseTypes.put(ColumnType.BINARY, new NativeType("blob"));
        nativeDatabaseTypes.put(ColumnType.BOOLEAN, new NativeType("tinyint", 1));
    }

    private String url;
    private Properties properties;

    /**
     * Construct a MySQL adapter with the specified URL and properties.
     * @param url The database URL
     * @param properties The properties
     */
    public MySqlAdapter(String url, Properties properties) {
        this.url = url;
        this.properties = properties;

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ActiveRecordException(e);
        }
    }

    /**
     * Get the adapter name.
     * @return The adapter name
     */
    public String getName() {
        return "MySQL";
    }

    /**
     * Return true to indicate that the adapter supports migrations.
     * @return True to indicate support for migrations
     */
    public boolean supportsMigrations() {
        return true;
    }

    /**
     * Get the columns for the specified table name.
     * @param tableName The table name
     * @return The collection of columns
     */
    public Collection<Column> getColumns(String tableName) {
        Collection<Column> columns = new LinkedList<Column>();
        String sql = "SHOW FIELDS FROM " + tableName;
        Rows rows = (Rows) execute(sql, Strings.camelize(tableName) + " Columns");
        for (Row row : rows) {
            Column column = new MySqlColumn(String.valueOf(row.get(0)), row.get(4), String.valueOf(row.get(1)),
                    row.get(2).equals("YES"));
            columns.add(column);
        }
        return columns;
    }

    /**
     * Execute the given SQL.
     * @param sql The SQL statement
     * @param name The logger name
     * @return The results
     */
    public Object execute(final String sql, final List parameters, String name) {
        final Object[] result = new Object[1];
        log(sql, name, new LogBlock() {
            public void execute() {
                Connection conn = null;
                try {
                    System.out.println(sql);
                    conn = getConnection();
                    PreparedStatement s = conn.prepareStatement(sql);
                    if (parameters != null) {
//                        System.out.println("parameter count: " + parameters.size());
                        for (int i = 1; i <= parameters.size(); i++) {
//                            System.out.println("parameter " + i + " = " + parameters.get(i - 1));
                            s.setObject(i, parameters.get(i - 1));
                        }
                    }
                    if (s.execute()) {
                        Rows rows = new Rows();
                        ResultSet rs = s.getResultSet();
                        ResultSetMetaData metaData = rs.getMetaData();
                        while (rs.next()) {
                            Row row = new Row();
                            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                                String name = metaData.getColumnName(i);
                                Object value = rs.getObject(i);
                                row.add(name, value);
                            }
                            rows.add(row);
                        }
                        result[0] = rows;
                    } else {
                        ResultSet krs = s.getGeneratedKeys();
                        if (krs != null && krs.next()) {
                            result[0] = krs.getLong(1);
                        } else {
                            result[0] = s.getUpdateCount();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new StatementInvalidException("Error executing SQL statement: " + e.getMessage());
                } finally {
                    SQL.close(conn);
                }
            }
        });
        return result[0];
    }

    /**
     * Get a map of column types to native types.
     * @return The ColumnType to NativeType map
     */
    public Map<ColumnType, NativeType> getNativeDatabaseTypes() {
        return nativeDatabaseTypes;
    }

    /** Begin a transaction. */
    public void beginTransaction() {
        execute("BEGIN");
    }

    /** Commit the current transaction. */
    public void commitTransaction() {
        execute("COMMIT");
    }

    /** Rollback the current transaction. */
    public void rollbackTransaction() {
        execute("ROLLBACK");
    }

    // connection methods

    /**
     * Get a database connection.
     * @return The database connection
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, properties);
    }

}
