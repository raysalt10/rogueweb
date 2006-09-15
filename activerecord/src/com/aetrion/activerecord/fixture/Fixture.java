package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activesupport.Strings;
import com.aetrion.activesupport.Classes;

import java.lang.reflect.Field;

/**
 * A fixture is used to prepare a database table for testing.
 *
 * @author Anthony Eden
 */
public class Fixture {

    /**
     * Construct a new Fixture
     */
    protected Fixture() {
        initializeDatabase();
        initialize();
        saveRecords();
    }

    /**
     * Subclasses implement this to set up fixtures.
     */
    protected void initialize() {
        // default does nothing
    }

    /**
     * Initializes the test database.
     */
    protected void initializeDatabase() {
        String className = getClass().getName();
//        System.out.println("Class name: " + className);
        String tableName = Strings.tableize(className);
//        System.out.println("Initializing " + tableName);
        Adapter adapter = Adapter.getDefaultAdapter();
        adapter.delete("DELETE FROM " + tableName);
    }

    protected void saveRecords() {
        for (Field f : getClass().getDeclaredFields()) {
            Object o = Classes.getFieldValue(this, f.getName());
            if (o instanceof ActiveRecord) {
                ActiveRecord ar = (ActiveRecord) o;
                ar.includePrimaryKey = true;
                ar.save();
                ar.includePrimaryKey = false;
            }
        }
    }
}

