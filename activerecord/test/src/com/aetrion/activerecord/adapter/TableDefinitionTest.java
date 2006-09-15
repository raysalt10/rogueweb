package com.aetrion.activerecord.adapter;

import junit.framework.TestCase;

/**
 * Test case for TableDefinition class.
 *
 * @author Anthony Eden
 */
public class TableDefinitionTest extends TestCase {

    public void testToSql() {
        Adapter adapter = Adapter.getDefaultAdapter();
        TableDefinition t = new TableDefinition(adapter);

        t.setPrimaryKey("id");
        assertEquals("id int(11) DEFAULT NULL auto_increment PRIMARY KEY", t.toSql());

        t.column("name", ColumnType.STRING);
        assertEquals("id int(11) DEFAULT NULL auto_increment PRIMARY KEY, name varchar(255)", t.toSql());

        t.column("age", ColumnType.INTEGER);
        assertEquals("id int(11) DEFAULT NULL auto_increment PRIMARY KEY, name varchar(255), age int(11)", t.toSql());


    }

}
