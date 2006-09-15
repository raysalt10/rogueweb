package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * 
 */
public class Project extends ActiveRecord {

    @HasAndBelongsToMany(unique = true, order = "developers.name desc, developers.id desc")
    private Collection<Developer> developers;

    @HasAndBelongsToMany(order = "developers.name desc, developers.id desc")
    private Collection<Developer> nonUniqueDevelopers;

    @HasAndBelongsToMany(limit = 1)
    private Collection<Developer> limitedDevelopers;

    @HasAndBelongsToMany(conditions = {"name = 'David'"})
    private Collection<Developer> developersNamedDavid;

    @HasAndBelongsToMany(conditions = "salary > 0")
    private Collection<Developer> salariedDevelopers;

    @HasAndBelongsToMany(finderSql = "SELECT t.*, j.* FROM developers_projects j, developers t WHERE t.id = j.developer_id AND j.project_id = $id'")
    private Collection<Developer> developersWithFinderSql;

    @HasAndBelongsToMany(deleteSql = "DELETE FROM developers_projects WHERE project_id = $id AND developer_id = $record_id")
    private Collection<Developer> developersBySql;

    @HasAndBelongsToMany(beforeAdd = "logBeforeAdding", afterAdd = "logAfterAdding", beforeRemove = "logBeforeRemoving", afterRemove = "logBeforeRemoving")
    private Collection<Developer> developersWithCallback;

    private String name;
    private List<String> developersLog;

    // accessor methods

    public Collection<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Collection<Developer> developers) {
        this.developers = developers;
    }

    public Collection<Developer> getNonUniqueDevelopers() {
        return nonUniqueDevelopers;
    }

    public void setNonUniqueDevelopers(Collection<Developer> nonUniqueDevelopers) {
        this.nonUniqueDevelopers = nonUniqueDevelopers;
    }

    public Collection<Developer> getLimitedDevelopers() {
        return limitedDevelopers;
    }

    public void setLimitedDevelopers(Collection<Developer> limitedDevelopers) {
        this.limitedDevelopers = limitedDevelopers;
    }

    public Collection<Developer> getDevelopersNamedDavid() {
        return developersNamedDavid;
    }

    public void setDevelopersNamedDavid(Collection<Developer> developersNamedDavid) {
        this.developersNamedDavid = developersNamedDavid;
    }

    public Collection<Developer> getSalariedDevelopers() {
        return salariedDevelopers;
    }

    public void setSalariedDevelopers(Collection<Developer> salariedDevelopers) {
        this.salariedDevelopers = salariedDevelopers;
    }

    public Collection<Developer> getDevelopersWithFinderSql() {
        return developersWithFinderSql;
    }

    public void setDevelopersWithFinderSql(Collection<Developer> developersWithFinderSql) {
        this.developersWithFinderSql = developersWithFinderSql;
    }

    public Collection<Developer> getDevelopersBySql() {
        return developersBySql;
    }

    public void setDevelopersBySql(Collection<Developer> developersBySql) {
        this.developersBySql = developersBySql;
    }

    public Collection<Developer> getDevelopersWithCallback() {
        return developersWithCallback;
    }

    public void setDevelopersWithCallback(Collection<Developer> developersWithCallback) {
        this.developersWithCallback = developersWithCallback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDevelopersLog() {
        return developersLog;
    }

    // callacks
    protected void logBeforeAdding(Object o) {
        developersLog.add("before_adding " + o);
    }

    protected void logAfterAdding(Object o) {
        developersLog.add("after adding " + o);
    }

    protected void logBeforeRemoving(Object o) {
        developersLog.add("before removing " + o);
    }

    protected void logAfterRemoving(Object o) {
        developersLog.add("after removing" + o);
    }

    protected void afterInitialize() {
        developersLog = new ArrayList<String>();
    }

}