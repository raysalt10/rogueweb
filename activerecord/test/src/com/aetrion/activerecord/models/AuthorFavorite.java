package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.ActiveRecord;

/**
 * Join model representing an author's favorite author.
 *
 * @author Anthony Eden
 */
public class AuthorFavorite extends ActiveRecord {

    @BelongsTo
    private Author author;

    private Object authorId;

    @BelongsTo(foreignKey = "favorite_author_id")
    private Author favoriteAuthor;

    private Object favoriteAuthorId;

    // accessors
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

    public Author getFavoriteAuthor() {
        return favoriteAuthor;
    }

    public void setFavoriteAuthor(Author favoriteAuthor) {
        this.favoriteAuthor = favoriteAuthor;
    }

    public Object getFavoriteAuthorId() {
        return favoriteAuthorId;
    }

    public void setFavoriteAuthorId(Object favoriteAuthorId) {
        this.favoriteAuthorId = favoriteAuthorId;
    }
}
