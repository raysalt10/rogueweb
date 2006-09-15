package com.aetrion.activerecord.adapter;

/**
 * Enumeration of SQL-standard column types.
 *
 * @author Anthony Eden
 */
public enum ColumnType {
    PRIMARY_KEY,

    INTEGER,
    FLOAT,
    DECIMAL,
    DATETIME,
    DATE,
    TIMESTAMP,
    TIME,
    TEXT,
    STRING,
    BINARY,
    BOOLEAN
}
