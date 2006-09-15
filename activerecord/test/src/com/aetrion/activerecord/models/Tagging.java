package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
public class Tagging extends ActiveRecord {

    @BelongsTo(include = Tagging.class)
    private Tag tag;

    @BelongsTo(foreignKey = "super_tag_id")
    private Tag superTag;

    @BelongsTo(polymorphic = true, counterCache = true)
    private Tag taggable;

    // attribute accessors

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getSuperTag() {
        return superTag;
    }

    public void setSuperTag(Tag superTag) {
        this.superTag = superTag;
    }

    public Tag getTaggable() {
        return taggable;
    }

    public void setTaggable(Tag taggable) {
        this.taggable = taggable;
    }
}
