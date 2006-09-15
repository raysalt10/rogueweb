package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.annotations.validations.Validate;
import com.aetrion.activerecord.annotations.validations.ValidateOnCreate;

/**
 * Reply to a Topic. Replies are Topics themselves, and thus can also be replied to.
 */
@Validate("errorsOnEmptyContent")
@ValidateOnCreate("titleIsWrongCreate")
public class Reply extends Topic {

    @BelongsTo(foreignKey = "parent_id", counterCache = true)
    private Topic topic;

    // accessor methods

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    // validations

    protected void validate() {
        if (getTitle() == null) errors.add("title", "Empty");
    }

    protected void errorsOnEmptyContent() {
        if (getContent() == null) errors.add("content", "Empty");
    }

    protected void validateOnCreate() {
        if (getContent() != null && getContent().equals("Mismatch")) errors.add("title", "is Content Mismatch");
    }

    protected void titleIsWrongCreate() {
        if (getTitle() != null && getTitle().equals("Wrong Create")) errors.add("title", "is Wrong Create");
    }

    protected void validateOnUpdate() {
        if (getTitle() != null && getTitle().equals("Wrong Update")) errors.add("title", "is Wrong Update");
    }

}
