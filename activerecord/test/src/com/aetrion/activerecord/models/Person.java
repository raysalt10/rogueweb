package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.HasMany;

import java.util.Collection;

/**
 * 
 */
public class Person extends ActiveRecord {

    @HasMany
    private Collection<Reader> readers;

    @HasMany(through = Reader.class)
    private Collection<Post> posts;

    private String firstName;

    public Collection<Reader> getReaders() {
        return readers;
    }

    public void setReaders(Collection<Reader> readers) {
        this.readers = readers;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
