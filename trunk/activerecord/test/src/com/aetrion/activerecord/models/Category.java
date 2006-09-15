package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.ActiveRecord;

import java.util.Collection;

/**
 * A class representing a category.
 *
 * @author Anthony Eden
 */
public class Category extends ActiveRecord {

    @HasAndBelongsToMany
    private Collection<Post> posts;

    @HasAndBelongsToMany
    private Collection<Post> specialPosts;

    @HasAndBelongsToMany(conditions = "posts.body = 'hello'")
    private Collection<Post> helloPosts;

    @HasAndBelongsToMany(conditions = "posts.body = 'nonexistent'")
    private Collection<Post> nonexistentPosts;

    @HasMany
    private Collection<Categorization> categorizations;

    @HasMany(through = Author.class, select = "authors.*, categorizations.post_id")
    private Collection<Author> authors;

    private String name;

    // accessors

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Collection<Post> getSpecialPosts() {
        return specialPosts;
    }

    public void setSpecialPosts(Collection<Post> specialPosts) {
        this.specialPosts = specialPosts;
    }

    public Collection<Post> getHelloPosts() {
        return helloPosts;
    }

    public void setHelloPosts(Collection<Post> helloPosts) {
        this.helloPosts = helloPosts;
    }

    public Collection<Post> getNonexistentPosts() {
        return nonexistentPosts;
    }

    public void setNonexistentPosts(Collection<Post> nonexistentPosts) {
        this.nonexistentPosts = nonexistentPosts;
    }

    public Collection<Categorization> getCategorizations() {
        return categorizations;
    }

    public void setCategorizations(Collection<Categorization> categorizations) {
        this.categorizations = categorizations;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // other methods

    public String whatAreYou() {
        return "a category...";
    }

}
