package com.aetrion.activerecord.adapter.mysql;

import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activerecord.adapter.ColumnType;

/**
 * Column implementation which adds support for tinyint for boolean emulation on MySQL as well as support for MySQL enum
 * types as Strings.
 * @author Anthony Eden
 */
public class MySqlColumn extends Column {

    public MySqlColumn(String name, ColumnType columnType) {
        super(name, columnType);
    }

    public MySqlColumn(String name, Object defaultValue, String sqlType, boolean nullAllowed) {
        super(name, defaultValue, sqlType, nullAllowed);
    }

    /**
     * Override the simplifiedType() method to support tinyint(1) as a boolean (emulation) and enums as Strings.
     * @param fieldType The field type
     * @return The simplified type
     */
    @Override
    protected ColumnType simplifiedType(String fieldType) {
        fieldType = fieldType.toLowerCase();
        if (MySqlAdapter.EMULATE_BOOLEANS && fieldType.contains("tinyint(1)")) return ColumnType.BOOLEAN;
        if (fieldType.contains("enum")) return ColumnType.STRING;
        return super.simplifiedType(fieldType);
    }
}
