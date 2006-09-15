package com.aetrion.activerecord.adapter;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.Callback;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.adapter.mysql.MySqlAdapter;
import com.aetrion.activerecord.errors.NotImplementedException;
import com.aetrion.activerecord.errors.StatementInvalidException;
import com.aetrion.activerecord.migration.Migration;
import com.aetrion.activesupport.Benchmark;
import com.aetrion.activesupport.Strings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base abstract database adapter. Implementations must extend from this class.
 * @author Anthony Eden
 */
public abstract class Adapter {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost/test";

    /** The default adapter. */
    private static Adapter defaultAdapter;

    /** The logger. */
    private Logger logger = Logger.global;

    /** The amount of time spent executing. */
    private double runtime;

    /**
     * Get the default adapter, used by all ActiveRecord objects which do not explicitly specify an adapter.
     * @return The default adapter
     */
    public static Adapter getDefaultAdapter() {
        if (defaultAdapter == null) {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "");
            defaultAdapter = new MySqlAdapter(DEFAULT_URL, properties);
        }
        return defaultAdapter;
    }

    /**
     * Set the default adapter, used by all ActiveRecord objects which do not explicitly specify an adapter.
     * @param adapter
     */
    public static void setDefaultAdapter(Adapter adapter) {
        if (adapter == null) throw new IllegalArgumentException("You cannot specify a null default adapter");
        defaultAdapter = adapter;
    }

    /**
     * Get the adapter name.
     * @return The adapter name
     */
    public String getName() {
        return "Adapter";
    }

    /**
     * Return the amount of time spent executing.
     * @return The number of seconds spent executing
     */
    public double getRuntime() {
        return runtime;
    }

    /** Reset the run time variable. */
    public void resetRuntime() {
        runtime = 0;
    }

    /**
     * Return true to indicate that the adapter supports migrations.
     * @return True to indicate support for migrations
     */
    public boolean supportsMigrations() {
        return false;
    }

    /**
     * Adapters which require setting of the primary key before storage should return true.
     * @param tableName The table name
     * @return True to indicate primary keys should be fetched before storage
     */
    public boolean shouldPrefetchPrimaryKey(String tableName) {
        return false;
    }

    /**
     * Return the next sequence value. The default implementation returns null.
     * @param tableName The table name
     * @return The next sequence value
     */
    public Long getNextSequenceValue(String tableName) {
        return null;
    }

    /**
     * Get the columns for the specified table name.
     * @param tableName The table name
     * @return The collection of columns
     */
    public abstract Collection<Column> getColumns(String tableName);

    // SQL methods

    /**
     * Execute the given SQL.
     * @param sql The SQL statement
     * @return The results
     */
    public Object execute(String sql) {
        return execute(sql, null, null);
    }

    /**
     * Execute the given SQL.
     * @param sql The SQL statement
     * @param name The name
     * @return The results
     */
    public Object execute(String sql, String name) {
        return execute(sql, null, name);
    }

    /**
     * Execute the given SQL.
     * @param sql The SQL statement
     * @param parameters The SQL parameters
     * @return The results
     */
    public Object execute(String sql, List parameters) {
        return execute(sql, parameters, null);
    }

    /**
     * Execute the given SQL.
     * @param sql The SQL statement
     * @param name The logger name
     * @return The results
     */
    public abstract Object execute(String sql, List parameters, String name);

    public Rows selectAll(String sql) {
        return selectAll(sql, null);
    }

    /**
     * Select all rows returned by the given SQL query.
     * @param sql The SQL query
     * @return The Rows
     */
    public Rows selectAll(String sql, List parameters) {
        return selectAll(sql, parameters, null);
    }

    /**
     * Select all rows returned by the given SQL query.
     * @param sql The SQL query
     * @param parameters SQL parameters
     * @param name The query name
     * @return The rows
     */
    public Rows selectAll(String sql, List parameters, String name) {
        return (Rows) execute(sql, parameters, name);
    }

    /**
     * Select only a single row returned by the given SQL query.
     * @param sql The SQL query
     * @return The Row
     */
    public Row selectOne(String sql) {
        return selectOne(sql, null);
    }

    /**
     * Select only a single row returned by the given SQL query.
     * @param sql The SQL query
     * @return The Row
     */
    public Row selectOne(String sql, List parameters) {
        return selectOne(sql, parameters, null);
    }

    /**
     * Select only a single row returned by the given SQL query.
     * @param sql The SQL
     * @param name The query name
     * @return The Row
     */
    public Row selectOne(String sql, List parameters, String name) {
        Rows rows = (Rows) execute(sql, parameters, name);
        if (rows.size() < 1) return null;
        return rows.getFirst();
    }

    public Object insert(String sql) {
        return insert(sql, null, null);
    }

    /**
     * Execute the SQL insert and return the primary key of the newly inserted record.
     * @param sql The SQL statement
     * @return The new primary key
     */
    public Object insert(String sql, List parameters) {
        return insert(sql, parameters, null);
    }

    public Object insert(String sql, String name) {
        return insert(sql, null, name);
    }

    /**
     * Execute the SQL insert and return the primary key of the newly inserted record.
     * @param sql The SQL statement
     * @param name The statement name
     * @return The new primary key
     */
    public Object insert(String sql, List parameters, String name) {
        return execute(sql, parameters, name);
    }

    /**
     * Execute the SQL update.
     * @param sql The SQL
     * @return The number of rows updated
     */
    public Integer update(String sql) {
        return update(sql, null, null);
    }

    /**
     * Execute the SQL update.
     * @param sql The SQL
     * @return The number of rows updated
     */
    public Integer update(String sql, List parameters) {
        return update(sql, parameters, null);
    }

    /**
     * Execute the SQL update.
     * @param sql The SQL
     * @param name The name for logging
     * @return The number of rows updated
     */
    public Integer update(String sql, String name) {
        return update(sql, null, name);
    }

    /**
     * Execute the SQL update.
     * @param sql The SQL
     * @param name The query name
     * @return The number of rows updated
     */
    public Integer update(String sql, List parameters, String name) {
        return (Integer) execute(sql, parameters, name);
    }

    /**
     * Execute the specified delete query.
     * @param sql The SQL for deleting
     * @return The number of rows deleted
     */
    public Integer delete(String sql) {
        return delete(sql, null, null);
    }

    /**
     * Execute the specified delete query.
     * @param sql The SQL for deleting
     * @return The number of rows deleted
     */
    public Integer delete(String sql, List parameters) {
        return delete(sql, parameters, null);
    }

    /**
     * Execute the specified delete query.
     * @param sql The SQL for deleting
     * @param name The name
     * @return The number of rows deleted
     */
    public Integer delete(String sql, String name) {
        return delete(sql, null, name);
    }

    /**
     * Executed the specified delete query, with the given name for logging.
     * @param sql The SQL query
     * @param name The name
     * @return The number of rows deleted
     */
    public Integer delete(String sql, List parameters, String name) {
        return (Integer) execute(sql, parameters, name);
    }

    // schema methods

    public void createTable(String tableName, AROptions options, Migration.TableCallback callback) {
        TableDefinition tableDefinition = new TableDefinition(this);
        if (options.id) {
            String primaryKey = options.primaryKey;
            if (options.primaryKey == null) primaryKey = "id";
            tableDefinition.setPrimaryKey(primaryKey);
        }

        if (options.force) {
            try {
                dropTable(tableName);
            } catch (StatementInvalidException e) {
                // do nothing
            }
        }

        if (callback != null) callback.execute(tableDefinition);

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE");
        if (options.temporary) {
            sb.append(" TEMPORARY");
        }
        sb.append(" TABLE ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(tableDefinition.toSql());
        sb.append(") ");
        sb.append(options.options);
        execute(sb.toString());
    }

    public void renameTable(String name, String newName) {
        throw new NotImplementedException("renameTable is not implemented");
    }

    public void dropTable(String name) {
        execute("DROP TABLE " + name);
    }

    public void addColumn(String tableName, String columnName, ColumnType type) {
        addColumn(tableName, columnName, type, new AROptions());
    }

    public void addColumn(String tableName, String columnName, ColumnType type, AROptions options) {
        StringBuilder addColumnSql = new StringBuilder();
        addColumnSql.append("ALTER TABLE ");
        addColumnSql.append(tableName);
        addColumnSql.append(" ADD ");
        addColumnSql.append(quoteColumnName(columnName));
        addColumnSql.append(" ");
        addColumnSql.append(typeToSql(type, options.limit, options.precision, options.scale));
        addColumnOptions(addColumnSql, options);
        execute(addColumnSql.toString());
    }

    public void removeColumn(String tableName, String columnName) {
        execute("ALTER TABLE " + tableName + " DROP " + quoteColumnName(columnName));
    }

    public void changeColumn(String tableName, String columnName, ColumnType type, AROptions options) {
        throw new NotImplementedException("changeColumn is not implemented");
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        throw new NotImplementedException("renameColumn is not implemented");
    }

    // type-related methods

    /**
     * Get a map of column types to native database types.
     * @return The native database type map
     */
    public abstract Map<ColumnType, NativeType> getNativeDatabaseTypes();

    /**
     * Convert the specified ColumnType to a SQL type.
     * @param type The ColumnType
     * @return The SQL string
     */
    public String typeToSql(ColumnType type) {
        return typeToSql(type, null, null, null);
    }

    /**
     * Convert the specified column type with the given arguments to a SQL fragment. If scale is specified then
     * precision must also be specified for an IllegalArgumentException will be thrown.
     * @param type The ColumnType
     * @param limit The limit
     * @param precision The precision
     * @param scale The scale
     * @return The SQL fragment
     */
    public String typeToSql(ColumnType type, Integer limit, Integer precision, Integer scale) {
        NativeType nativeType = getNativeDatabaseTypes().get(type);
        StringBuilder columnTypeSql = new StringBuilder(nativeType.getName());
        if (type == ColumnType.DECIMAL) {
            if (precision == null) precision = nativeType.getPrecision();
            if (scale == null) scale = nativeType.getScale();
            if (precision != null) {
                if (scale != null) {
                    columnTypeSql.append("(").append(precision).append(",").append(scale).append(")");
                } else {
                    columnTypeSql.append("(").append(precision).append(")");
                }
            } else if (scale != null) {
                throw new IllegalArgumentException(
                        "Error adding decimal column: precision cannot be empty if scale is specified");
            }
        } else {
            if (limit == null) limit = nativeType.getLimit();
            if (limit != null) columnTypeSql.append("(").append(limit).append(")");
        }
        return columnTypeSql.toString();
    }

    // quoting

    public String quote(Object value) {
        return quote(value, null);
    }

    /**
     * Quote the value.
     * @param value The value
     * @param column The column or null
     * @return The quoted value
     */
    public String quote(Object value, Column column) {
        if (value == null) return "NULL";

        if (value instanceof ActiveRecord) {
            return quote(((ActiveRecord) value).getId()); // if its a record then return the quoted primary key
        }

        if (value instanceof String) {
            if (column != null && column.getType() == ColumnType.BINARY) {
                // return "'" + quoteString(column.stringToBinary(value)) + "'"; // TODO implement properly
                throw new NotImplementedException("Binary types not currently supported");
            } else
            if (column != null && (column.getType() == ColumnType.INTEGER || column.getType() == ColumnType.FLOAT)) {
                if (column.getType() == ColumnType.INTEGER) {
                    Integer i = Integer.parseInt((String) value);
                    return i.toString();
                } else {
                    Float f = Float.parseFloat((String) value);
                    return f.toString();
                }
            } else {
                return "'" + quoteString((String) value) + "'";
            }
        }
        if (value instanceof Boolean) {
            if ((Boolean) value) {
                if (column != null && column.getType() == ColumnType.INTEGER) return "1";
                return quotedTrue();
            } else {
                if (column != null && column.getType() == ColumnType.INTEGER) return "0";
                return quotedFalse();
            }
        }
        if (value instanceof Float || value instanceof BigInteger) return value.toString();
        if (value instanceof BigDecimal) return value.toString(); // TODO verify implementation
        if (value instanceof Date) return "'" + quotedDate((Date) value) + "'";
        return "'" + quoteString(value.toString()) + "'"; // TODO implement as serializable?
    }

    /**
     * Quote the String
     * @param s The String
     * @return The quoted string
     */
    public String quoteString(String s) {
        return s.replaceAll("\\\\", "&&").replaceAll("'", "''");
    }

    /**
     * Quote the column name
     * @param columnName The column name
     * @return The quoted column name
     */
    public String quoteColumnName(String columnName) {
        return columnName;
    }

    public String quotedTrue() {
        return "'t'";
    }

    /**
     * Quoted representation of false
     * @return The quoted value
     */
    public String quotedFalse() {
        return "'f'";
    }

    /**
     * Convert the date to a quoted string.
     * @param date The date
     * @return The quoted date string
     */
    public String quotedDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    // transactions

    /**
     * Execute the callback in a transaction. Returns the return value from the Callback.
     * @param callback The Callback
     * @return The return value from the Callback
     */
    public Object transaction(Callback callback) {
        return transaction(callback, true);
    }

    /**
     * Execute the callback in a transaction. If startTransaction is set to false then a new transaction will not be
     * started (i.e. there must already be an open transaction).
     * @param callback The Callback
     * @param startTransaction True to start a new transaction
     * @return The return value of the callback
     */
    public Object transaction(Callback callback, boolean startTransaction) {
        boolean transactionOpen = false;
        try {
            if (callback != null) {
                if (startTransaction) {
                    beginTransaction();
                    transactionOpen = true;
                }
                return callback.execute();
            } else {
                return null;
            }
        } catch (RuntimeException t) {
            if (transactionOpen) {
                transactionOpen = false;
                rollbackTransaction();
            }
            throw t;
        } finally {
            if (transactionOpen) {
                commitTransaction();
            }
        }
    }

    /** Begin the transaction. */
    public abstract void beginTransaction();

    /** Commit the transaction. */
    public abstract void commitTransaction();

    /** Rollback the transaction. */
    public abstract void rollbackTransaction();

    // utility methods for adding SQL options

    /**
     * Add the options to the SQL.
     * @param sql The SQL
     * @param options The options
     * @return The SQL parameters
     */
    public List addOptions(StringBuilder sql, AROptions options) {
        if (options == null) return new ArrayList();
        List parameters = addConditions(sql, options);
        addLimit(sql, options);
        addLock(sql, options);
        addColumnOptions(sql, options);
        return parameters;
    }

    List addConditions(StringBuilder sql, AROptions options) {
        List<Object> parameters = new ArrayList<Object>();
        if (options.conditions != null && options.conditions.size() > 0) {
            sql.append(" WHERE ");
            List<String> queryList = new ArrayList<String>();
            for (Conditions c : options.conditions) {
                System.out.println("adding condition " + c.getSql() + " with " + c.getParameters().size() + " parameters");
                queryList.add(c.getSql());
                parameters.addAll(c.getParameters());
            }
            sql.append(Strings.join(queryList, " AND "));
        }
        return parameters;
    }

    void addLimit(StringBuilder sql, AROptions options) {
        if (options != null) addLimitOffset(sql, options);
    }

    void addLimitOffset(StringBuilder sql, AROptions options) {
        if (options.limit != null) {
            sql.append(" LIMIT ");
            sql.append(options.limit);
            if (options.offset != null) {
                sql.append(" OFFSET ");
                sql.append(options.offset);
            }
        }
    }

    void addLock(StringBuilder sql, AROptions options) {
        if (options.lock != null) {
            Object lock = options.lock;
            if (lock instanceof Boolean && ((Boolean) lock)) {
                sql.append(" FOR UPDATE");
            } else if (lock instanceof String) {
                sql.append(" ");
                sql.append(lock);
            }
        }
    }

    void addJoins(StringBuilder sql, AROptions options) {
        if (options.joins != null) {
            sql.append(" ");
            sql.append(options.joins);
            sql.append(" ");
        }
    }

    protected void addColumnOptions(StringBuilder sql, AROptions options) {
        if (options.defaultValue != null) {
            String defaultValue = quote(options.defaultValue, options.column);
            sql.append(" DEFAULT ").append(defaultValue);
        }
        if (!options.nullAllowed) {
            sql.append(" NOT NULL");
        }
    }

    // logging methods

    protected void log(String sql, String name, final LogBlock block) {
        if (block != null) {
            if (logger != null && logger.getLevel() == Level.INFO) {
                double seconds = Benchmark.realtime(new Benchmark.BenchmarkBlock() {
                    public void execute() {
                        block.execute();
                    }
                });
                runtime = seconds;
                log(sql, name, seconds);
            } else {
                block.execute();
            }
        } else {
            log(sql, name, 0);
        }
    }

    /**
     * Log a message about the execution of the specified SQL statement. If name is null then "SQL" will be used.
     * @param sql The SQL statement
     * @param name The name to use
     * @param seconds The number of seconds for the execution
     */
    protected void log(String sql, String name, double seconds) {
        if (name == null) name = "SQL";
        System.out.println(name + "(" + seconds + ") " + sql.replaceAll(" +", " "));
    }

    public interface LogBlock {
        public void execute();
    }

}
