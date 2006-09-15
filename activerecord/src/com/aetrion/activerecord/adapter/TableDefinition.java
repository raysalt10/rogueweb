package com.aetrion.activerecord.adapter;

import com.aetrion.activerecord.AROptions;
import com.aetrion.activesupport.Strings;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 */
public class TableDefinition {

    private List<ColumnDefinition> columns;
    private Adapter adapter;

    public TableDefinition(Adapter adapter) {
        this.columns = new ArrayList<ColumnDefinition>();
        this.adapter = adapter;
    }
    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    public void setPrimaryKey(String name) {
        column(name, ColumnType.PRIMARY_KEY);
    }

    public ColumnDefinition getColumn(String name) {
        for (ColumnDefinition c : columns) {
            if (c.getName().equals(name)) return c;
        }
        return null;
    }

    public String toSql() {
        return Strings.join(columns, ", ");
    }

    public void column(String columnName, ColumnType type) {
        column(columnName, type, new AROptions());
    }
    public void column(String name, ColumnType type, AROptions options) {
        ColumnDefinition column = getColumn(name);
        Map<ColumnType, NativeType> nativeTypes = adapter.getNativeDatabaseTypes();
        if (column == null) column = new ColumnDefinition(adapter, name, type);
        if (options.limit == null && nativeTypes.get(type) != null) {
            column.limit = nativeTypes.get(type).getLimit();
        } else {
            column.limit = options.limit;
        }
        column.precision = options.precision;
        column.scale = options.scale;
        column.defaultValue = options.defaultValue;
        column.nullAllowed = options.nullAllowed;
        if (!columns.contains(column)) columns.add(column);
    }
}


