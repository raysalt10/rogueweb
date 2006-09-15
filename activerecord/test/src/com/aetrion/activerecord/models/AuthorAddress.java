package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
public class AuthorAddress extends ActiveRecord {

    @HasOne
    private Author author;

    private Object authorId;

    // accessor methods
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Object getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Object authorId) {
        this.authorId = authorId;
    }

}
