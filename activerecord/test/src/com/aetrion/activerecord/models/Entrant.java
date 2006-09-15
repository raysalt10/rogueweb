package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.BelongsTo;

/**
 * An entrant in a course.
 *
 * @author Anthony Eden
 */
public class Entrant extends ActiveRecord {

    @BelongsTo
    private Course course;

    private String name;

    // accessor methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
