package com.aetrion.activerecord;

import junit.framework.TestCase;
import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.migration.Schema;
import com.aetrion.activerecord.errors.StatementInvalidException;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 */
public class ARSchemaTest extends TestCase {

    public void testSchemaDesign() {
        Adapter adapter = Adapter.getDefaultAdapter();
        try {
            adapter.execute("DROP TABLE fruits");
        } catch (StatementInvalidException e) {
            // do nothing
        }

        new ARTestSchema().execute();

        assertNotNull(adapter.execute("SELECT * FROM fruits"));
        assertNotNull(adapter.execute("SELECT * FROM schema_info"));
        assertEquals(7, adapter.selectOne("SELECT version FROM schema_info").get("version"));
    }

    class ARTestSchema extends Schema {

        protected Map<String, Object> define() {
            System.out.println("creating table fruits");
            createTable("fruits", new TableCallback() {
                public void execute(TableDefinition t) {
                    System.out.println("adding columns to table definition");
                    t.column("color", ColumnType.STRING);
                    t.column("fruit_size", ColumnType.STRING);
                    t.column("texture", ColumnType.STRING);
                    t.column("flavor", ColumnType.STRING);
                }
            });
            Map<String, Object> info = new HashMap<String, Object>();
            info.put("version", 7);
            return info;
        }
    }

}