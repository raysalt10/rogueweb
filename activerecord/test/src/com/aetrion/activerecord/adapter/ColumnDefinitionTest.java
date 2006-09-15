package com.aetrion.activerecord.adapter;

import junit.framework.TestCase;

/**
 * Test ColumnDefinition class.
 *
 * @author Anthony Eden
 */
public class ColumnDefinitionTest extends TestCase {

    /**
     * Test the ColumnDefinition.toSql method.
     */
    public void testToSql() {
        Adapter adapter = Adapter.getDefaultAdapter();
        ColumnDefinition columnDefinition = new ColumnDefinition(adapter, "name", ColumnType.STRING);
        assertEquals("name varchar(255)", columnDefinition.toSql());
    }

    public void testTypeToSql() {
        Adapter adapter = Adapter.getDefaultAdapter();
        ColumnDefinition columnDefinition = new ColumnDefinition(adapter, "", ColumnType.STRING);

        assertEquals("varchar(255)", columnDefinition.typeToSql(ColumnType.STRING, null, null, null));
        assertEquals("varchar(10)", columnDefinition.typeToSql(ColumnType.STRING, 10, null, null));
        assertEquals("varchar(255)", columnDefinition.typeToSql(ColumnType.STRING, null, 20, null));

        assertEquals("decimal", columnDefinition.typeToSql(ColumnType.DECIMAL, null, null, null));
        assertEquals("decimal", columnDefinition.typeToSql(ColumnType.DECIMAL, 10, null, null));
        assertEquals("decimal(10)", columnDefinition.typeToSql(ColumnType.DECIMAL, null, 10, null));
        assertEquals("decimal(10,2)", columnDefinition.typeToSql(ColumnType.DECIMAL, null, 10, 2));
    }

}
