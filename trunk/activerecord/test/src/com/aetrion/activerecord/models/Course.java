package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.HasMany;

/**
 * 
 */
public class Course extends ActiveRecord {

    @HasMany
    private Entrant entrants;

    private String name;

    // accessor methods

    public Entrant getEntrants() {
        return entrants;
    }

    public void setEntrants(Entrant entrants) {
        this.entrants = entrants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
