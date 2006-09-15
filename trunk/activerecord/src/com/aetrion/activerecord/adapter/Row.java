package com.aetrion.activerecord.adapter;

import java.util.*;

/**
 * A Row represents a single row from the database.
 *
 * @author Anthony Eden
 */
public class Row extends LinkedList<Object> {

    private Map<String,Integer> names = new HashMap<String, Integer>();

    /**
     * Get the named value.
     * @param name The name
     * @return The value
     */
    public Object get(String name) {
        Integer index = names.get(name);
        if (index != null) return get(index);
        return null;
    }

    /**
     * Add the named value.
     * @param name The name
     * @param value The value
     */
    public void add(String name, Object value) {
        add(value);
        names.put(name, size() - 1);

    }
}
