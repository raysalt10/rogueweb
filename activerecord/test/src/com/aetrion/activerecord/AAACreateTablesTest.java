package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.openbase.OpenBaseAdapter;
import com.aetrion.activerecord.errors.StatementInvalidException;
import com.aetrion.activesupport.IO;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

/**
 * 
 */
public class AAACreateTablesTest extends TestCase {

    private String basePath;

    public void setUp() {
        basePath = new File("activerecord/test").getAbsolutePath();
    }

    @Test public void testDropAndCreateMainTables() {
        recreate();
        assertTrue(true);
    }

    @Test public void testLoadSchema() {
        Adapter adapter = Adapter.getDefaultAdapter();
        if (adapter.supportsMigrations()) {
            new TestSchema().define();
        } else {
            recreate("2");
        }
        assertTrue(true);
    }

    // internal methods

    private void recreate() {
        recreate(null);
    }

    private void recreate(String suffix) {
        Adapter adapter = Adapter.getDefaultAdapter();
        StringBuilder sb = new StringBuilder();
        sb.append(adapter.getName().toLowerCase());
        if (suffix != null) sb.append(suffix);
        String adapterName = sb.toString();
        try {
            executeSqlFile(new File(basePath, "fixtures/db_definitions/" + adapterName + ".drop.sql"), adapter);
        } catch (StatementInvalidException e) {
            // do nothing
        }
        executeSqlFile(new File(basePath, "fixtures/db_definitions/" + adapterName + ".sql"), adapter);
    }

    private void executeSqlFile(File file, Adapter adapter) {
        // OpenBase has a different format for SQL files
        if (adapter instanceof OpenBaseAdapter) {
            String[] sql = IO.read(file).split("go");
            for (int i = 0; i < sql.length; i++) {
                String statement = sql[i].trim();
                // OpenBase does not support comments embedded in SQL
                if (!statement.equals("")) adapter.execute(statement, "SQL Statement " + i);
            }
        } else {
            String[] sql = IO.read(file).split(";");
            for (int i = 0; i < sql.length; i++) {
                String statement = sql[i].trim();
                if (!statement.equals("")) adapter.execute("\n\n-- statement #" + i + "\n" + statement + "\n");
            }
        }
    }

}
