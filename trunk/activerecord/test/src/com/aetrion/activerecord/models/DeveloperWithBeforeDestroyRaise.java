package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.Table;
import com.aetrion.activerecord.annotations.callbacks.BeforeDestroy;
import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;
import com.aetrion.activerecord.ActiveRecord;

import java.util.Collection;

/**
 * 
 */
@Table("developers")
public class DeveloperWithBeforeDestroyRaise extends ActiveRecord {

    @HasAndBelongsToMany(joinTable = "developers_projects", foreignKey = "developer_id")
    private Collection<Project> projects;

    @BeforeDestroy
    private void raiseIfProjectsEmpty() {
        if (projects.isEmpty()) throw new RuntimeException();
    }
}