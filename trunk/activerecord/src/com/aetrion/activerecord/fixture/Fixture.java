package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.errors.ActiveRecordException;
import com.aetrion.activesupport.Strings;
import com.aetrion.activesupport.Classes;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A fixture is used to prepare a database table for testing.
 *
 * Note: this class is not thread safe
 *
 * @author Anthony Eden
 */
public class Fixture implements List {

    private List<Object> fields;

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

    protected List<Object> getFields() {
        if (fields == null) {
            fields = new ArrayList<Object>();
            for (Field f : getClass().getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    fields.add(f.get(this));
                } catch (IllegalAccessException e) {
                    throw new ActiveRecordException(e);
                } finally {
                    f.setAccessible(false);
                }
            }
        }
        return fields;
    }

    public int size() {
        return getFields().size();
    }

    public boolean isEmpty() {
        return getFields().isEmpty();
    }

    public boolean contains(Object o) {
        return getFields().contains(o);
    }

    public Iterator iterator() {
        return getFields().iterator();
    }

    public Object[] toArray() {
        return getFields().toArray();
    }

    public boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Object get(int i) {
        return getFields().get(i);
    }

    public Object set(int i, Object o) {
        throw new UnsupportedOperationException();
    }

    public void add(int i, Object o) {
        throw new UnsupportedOperationException();
    }

    public Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        return getFields().indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return getFields().lastIndexOf(o);
    }

    public ListIterator listIterator() {
        return getFields().listIterator();
    }

    public ListIterator listIterator(int i) {
        return getFields().listIterator(i);
    }

    public List subList(int i, int i1) {
        return getFields().subList(i, i1);
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection collection) {
        return getFields().containsAll(collection);
    }

    public Object[] toArray(Object[] objects) {
        return getFields().toArray(objects);
    }
}

