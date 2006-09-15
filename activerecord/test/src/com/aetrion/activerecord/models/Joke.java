package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.Table;
import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
@Table("funny_jokes")
public class Joke extends ActiveRecord {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
