package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;

import java.util.Date;

/**
 * 
 */
public class Task extends ActiveRecord {
    private Date starting;
    private Date ending;

    public Date getStarting() {
        return starting;
    }

    public void setStarting(Date starting) {
        this.starting = starting;
    }

    public Date getEnding() {
        return ending;
    }

    public void setEnding(Date ending) {
        this.ending = ending;
    }
}
