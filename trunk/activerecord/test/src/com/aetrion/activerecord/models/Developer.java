package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.annotations.Table;
import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;
import com.aetrion.activerecord.annotations.validations.ValidatesInclusionOf;
import com.aetrion.activerecord.annotations.validations.ValidatesLengthOf;

import java.util.Collection;

/**
 * Class representing a Developer.
 *
 * @author Anthony Eden
 */
@Table("developers")
public class Developer extends ActiveRecord {

    @HasAndBelongsToMany(extend = DeveloperFindMostRecent.class)
    private Collection<Project> projects;

    @HasAndBelongsToMany(joinTable = "developers_projects", associationForeignKey = "project_id",
            extend = DeveloperProjectsAssociationExtension.class)
    private Collection<Project> projectsExtendedByName;

    @HasAndBelongsToMany(joinTable = "developers_projects", associationForeignKey = "project_id",
            extend = {DeveloperProjectsAssociationExtension.class, DeveloperProjectsAssociationExtension2.class})
    private Collection<Project> projectsExtendedByNameTwice;

    @HasAndBelongsToMany(joinTable = "developers_projects", associationForeignKey = "project_id")
    private Collection<Project> specialProjects;

    @ValidatesLengthOf(within = {3,20})
    private String name;

    @ValidatesInclusionOf(in = {5000,200000})
    private Integer salary;


    class DeveloperFindMostRecent {
        public Object findMostRecent() {
            AROptions options = new AROptions();
            return ActiveRecords.findFirst(Developer.class, options);
        }
    }

    class DeveloperProjectsAssociationExtension {
        public Object findMostRecent() {
            AROptions options = new AROptions("order", "id DESC");
            return ActiveRecords.findFirst(Developer.class, options);
        }
    }

    class DeveloperProjectsAssociationExtension2 {
        public Object findLeastRecent() {
            return ActiveRecords.findFirst(Developer.class, new AROptions("order", "id ASC"));
        }
    }

}