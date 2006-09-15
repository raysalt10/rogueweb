package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
public class Movie extends ActiveRecord {

    private String name;
    private Long movieid;

    // accessor methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMovieid() {
        return movieid;
    }

    public String getPrimaryKey() {
        return "movieid";
    }
}
