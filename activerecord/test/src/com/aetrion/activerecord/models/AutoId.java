package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
public class AutoId extends ActiveRecord {

    private Integer value;

    public String getTableName() {
        return "auto_id_tests";
    }

    public String getPrimaryKey() {
        return "auto_id";
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
