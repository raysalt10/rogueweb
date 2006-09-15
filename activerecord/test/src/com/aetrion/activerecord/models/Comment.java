package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.annotations.associations.BelongsTo;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A comment on a post.
 *
 * @author Anthony Eden
 */
public class Comment extends ActiveRecord {

    @BelongsTo
    private Post post;

    private String body;

    public static Collection<Comment> searchByType(String q) {
        AROptions options = new AROptions();
        options.addConditions("x = ?", q);
        return cast(ActiveRecords.findAll(Comment.class, options));
    }

    // accessor methods

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // other methods

    public String whatAreYou() {
        return "a comment...";
    }

    // private methods

    /**
     * Cast an untyped collection to the typed collection of Comment objects.
     * @param objects The untyped collection
     * @return The collection of Comment objects
     */
    private static Collection<Comment> cast(Collection objects) {
        Collection<Comment> comments = new LinkedList<Comment>();
        for (Object o : objects) {
            comments.add((Comment) o);
        }
        return comments;
    }
}
