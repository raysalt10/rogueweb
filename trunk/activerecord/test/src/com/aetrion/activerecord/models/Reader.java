package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.BelongsTo;

/**
 * Join table between people and posts.
 */
public class Reader extends ActiveRecord {

    @BelongsTo
    private Post post;

    @BelongsTo
    private Person person;

}
