package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.ColumnDefinition;
import com.aetrion.activerecord.adapter.Conditions;
import com.aetrion.activesupport.Options;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Collection of options.
 * @author Anthony Eden
 */
public class AROptions extends Options {
    public String order;
    public List<Conditions> conditions = new LinkedList<Conditions>();
    public String from;
    public String select;
    public String joins;
    public Object lock;

    public Integer precision;
    public Integer scale;
    public Integer limit;
    public Integer offset;

    public boolean id = true;
    public boolean force = false;
    public Object defaultValue;
    public String primaryKey;
    public boolean nullAllowed = true;
    public boolean temporary = false;

    public String options = "";

    public ColumnDefinition column;

    /**
     * Construct options from the Object array.
     * @param options The object array
     */
    public AROptions(Object... options) {
        super(options);
    }

    /**
     * Add conditions to the options.
     * @param conditions The conditions
     */
    public void addConditions(Conditions conditions) {
        this.conditions.add(conditions);
    }

    /**
     * Add conditions to the options.
     * @param sql The SQL
     */
    public void addConditions(String sql) {
        addConditions(new Conditions(sql));
    }

    /**
     * Add conditions to the options.
     * @param sql The SQL
     * @param parameters The parameters
     */
    public void addConditions(String sql, String... parameters) {
        List<String> params = Arrays.asList(parameters);
        Conditions conditions = new Conditions(sql, params);
        addConditions(conditions);
    }
}
