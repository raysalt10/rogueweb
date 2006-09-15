package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.ActiveRecord;

import java.util.Collection;

/**
 * 
 */
public class Tag extends ActiveRecord {

    @HasMany
    private Collection<Tagging> taggings;

    @HasMany(through = Tagging.class)
    private Collection<Tagging> taggables;

    @HasOne
    private Tagging tagging;

    private String name;

    // accessor methods

    public Collection<Tagging> getTaggings() {
        return taggings;
    }

    public void setTaggings(Collection<Tagging> taggings) {
        this.taggings = taggings;
    }

    public Collection<Tagging> getTaggables() {
        return taggables;
    }

    public void setTaggables(Collection<Tagging> taggables) {
        this.taggables = taggables;
    }

    public Tagging getTagging() {
        return tagging;
    }

    public void setTagging(Tagging tagging) {
        this.tagging = tagging;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}