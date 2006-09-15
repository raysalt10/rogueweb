package com.aetrion.activerecord.adapter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulation of SQL conditions.
 *
 * @author Anthony Eden
 */
public class Conditions {

    private String sql;
    private List<? extends Object> parameters = new LinkedList<Object>();

    public Conditions(String sql) {
        this.sql = sql;
    }

    public Conditions(String... conditions) {
        LinkedList<Object> list = new LinkedList<Object>(Arrays.asList(conditions));
        this.sql = String.valueOf(list.removeFirst());
        this.parameters = list;
    }

    public Conditions(String sql, List<? extends Object> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return sql;
    }

    public List<? extends Object> getParameters() {
        return parameters;
    }

}
