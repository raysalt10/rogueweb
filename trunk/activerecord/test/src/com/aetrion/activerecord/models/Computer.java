package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.BelongsTo;

/**
 * 
 */
public class Computer extends ActiveRecord {

    @BelongsTo(foreignKey = "developer")
    private Developer developer;

    private boolean extendedWarrenty;

    // accessor methods
    
    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public boolean isExtendedWarrenty() {
        return extendedWarrenty;
    }

    public void setExtendedWarrenty(boolean extendedWarrenty) {
        this.extendedWarrenty = extendedWarrenty;
    }

}