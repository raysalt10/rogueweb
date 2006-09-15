package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activerecord.adapter.mysql.MySqlAdapter;
import junit.framework.TestCase;

import java.util.Collection;
import java.util.Properties;

/**
 * 
 */
public class AdapterTest extends TestCase {

    public void testGetColumns() {
        Adapter adapter = Adapter.getDefaultAdapter();
        Collection<Column> columns = adapter.getColumns("accounts");
        assertEquals(3, columns.size());
    }

    public void testSetAdapterProperties() {
        Properties props = new Properties();
        props.setProperty("user", "bubba");
        props.setProperty("password", "foo");
        Adapter.setDefaultAdapter(new MySqlAdapter("jdbc:mysql://localhost/test", props));
        Adapter adapter = Adapter.getDefaultAdapter();
        adapter.getColumns("people");
    }

}
