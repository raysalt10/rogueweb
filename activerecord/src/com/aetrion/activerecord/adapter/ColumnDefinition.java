package com.aetrion.activerecord.adapter;

import com.aetrion.activerecord.AROptions;

/**
 * Definition of a column.
 *
 * @author Anthony Eden
 */
public class ColumnDefinition extends Column {

    private Adapter adapter;

    public ColumnDefinition(Adapter adapter, String name, ColumnType type) {
        super(name, type);
        this.adapter = adapter;
    }

    public String toSql() {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append(adapter.quoteColumnName(name));
        columnSql.append(" ");
        columnSql.append(typeToSql(type, limit, precision, scale));

        AROptions options = new AROptions();
        options.nullAllowed = nullAllowed;
        options.defaultValue = defaultValue;
        addColumnOptions(columnSql, options);

        return columnSql.toString();
    }

    public String toString() {
        return toSql();
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    String typeToSql(ColumnType type, Integer limit, Integer precision, Integer scale) {
        return adapter.typeToSql(type, limit, precision, scale);
    }

    void addColumnOptions(StringBuilder columnSql, AROptions options) {
        options.column = this;
        adapter.addColumnOptions(columnSql, options);
    }

}
