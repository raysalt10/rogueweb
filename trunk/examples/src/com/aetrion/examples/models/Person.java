package com.aetrion.examples.models;

import com.aetrion.activerecord.ActiveRecord;

import java.util.Date;

/**
 * 
 */
public class Person extends ActiveRecord {
    private String name;
    private Date createdOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
