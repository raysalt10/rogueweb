package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.ActiveRecord;

/**
 * Join model for posts, categories and authors.
 *
 * @author Anthony Eden
 */
public class Categorization extends ActiveRecord {

    @BelongsTo
    private Post post;

    private Object postId;

    @BelongsTo
    private Category category;

    private Object categoryId;

    @BelongsTo
    private Author author;

    private Object authorId;

    // accessors

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
