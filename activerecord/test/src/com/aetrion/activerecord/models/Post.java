package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.annotations.associations.HasOne;

import java.util.Collection;

/**
 * 
 */
public class Post extends ActiveRecord {

    @BelongsTo(extend = AuthorGreeting.class)
    private Author author;

    @BelongsTo(include = Post.class)
    private Author authorWithPosts;

    @HasMany(order = "body", extend = CommentsExtension.class)
    private Comment comments;

    @HasOne
    private VerySpecialComment verySpecialComment;

    @HasOne(include = Post.class)
    private VerySpecialComment verySpecialCommentWithPost;

    @HasMany
    private Collection<SpecialComment> specialComments;

    @HasAndBelongsToMany
    private Collection<Category> categories;

    @HasAndBelongsToMany(joinTable = "categories_posts", associationForeignKey = "category_id")
    private Collection<SpecialCategory> specialCategories;

    @HasMany(as = "taggable")
    private Collection<Tagging> taggings;

    @HasMany(through = Tagging.class, include = Tagging.class, extend = TagsExtension.class)
    private Collection<Tag> tags;

    @HasMany(through = Tagging.class, source = Tag.class)
    private Collection<Tag> funkyTags;

    @HasMany(through = Tagging.class)
    private Collection<Tag> superTags;

    @HasOne(as = "taggable")
    private Tagging tagging;

    @HasMany(as = "taggable", conditions = {"taggings.id < 0"})
    private Collection<Tagging> invalidTaggings;

    @HasMany(foreignKey = "category_id")
    private Collection<Categorization> categorizations;

    @HasMany(through = Categorization.class)
    private Collection<Author> authors;

    @HasMany
    private Collection<Reader> readers;

    @HasMany(through = Reader.class)
    private Collection<Person> people;

    private String title;
    private String body;

    // accessor methods

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthorWithPosts() {
        return authorWithPosts;
    }

    public void setAuthorWithPosts(Author authorWithPosts) {
        this.authorWithPosts = authorWithPosts;
    }

    public Comment getComments() {
        return comments;
    }

    public void setComments(Comment comments) {
        this.comments = comments;
    }

    public VerySpecialComment getVerySpecialComment() {
        return verySpecialComment;
    }

    public void setVerySpecialComment(VerySpecialComment verySpecialComment) {
        this.verySpecialComment = verySpecialComment;
    }

    public VerySpecialComment getVerySpecialCommentWithPost() {
        return verySpecialCommentWithPost;
    }

    public void setVerySpecialCommentWithPost(VerySpecialComment verySpecialCommentWithPost) {
        this.verySpecialCommentWithPost = verySpecialCommentWithPost;
    }

    public Collection<SpecialComment> getSpecialComments() {
        return specialComments;
    }

    public void setSpecialComments(Collection<SpecialComment> specialComments) {
        this.specialComments = specialComments;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }

    public Collection<SpecialCategory> getSpecialCategories() {
        return specialCategories;
    }

    public void setSpecialCategories(Collection<SpecialCategory> specialCategories) {
        this.specialCategories = specialCategories;
    }

    public Collection<Tagging> getTaggings() {
        return taggings;
    }

    public void setTaggings(Collection<Tagging> taggings) {
        this.taggings = taggings;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    public Collection<Tag> getFunkyTags() {
        return funkyTags;
    }

    public void setFunkyTags(Collection<Tag> funkyTags) {
        this.funkyTags = funkyTags;
    }

    public Collection<Tag> getSuperTags() {
        return superTags;
    }

    public void setSuperTags(Collection<Tag> superTags) {
        this.superTags = superTags;
    }

    public Tagging getTagging() {
        return tagging;
    }

    public void setTagging(Tagging tagging) {
        this.tagging = tagging;
    }

    public Collection<Tagging> getInvalidTaggings() {
        return invalidTaggings;
    }

    public void setInvalidTaggings(Collection<Tagging> invalidTaggings) {
        this.invalidTaggings = invalidTaggings;
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

    public Collection<Reader> getReaders() {
        return readers;
    }

    public void setReaders(Collection<Reader> readers) {
        this.readers = readers;
    }

    public Collection<Person> getPeople() {
        return people;
    }

    public void setPeople(Collection<Person> people) {
        this.people = people;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // other methods

    public String whatAreYou() {
        return "a post...";
    }

    // extension classes
    class AuthorGreeting {
        public String greeting() {
            return "hello";
        }
    }

    class CommentsExtension {
        public Object findMostRecent() {
            return ActiveRecords.findFirst(Post.class, new AROptions("order", "id DESC"));
        }
    }

    class TagsExtension {
        public Object addJoinsAndSelect() {
            AROptions options = new AROptions();
            options.select = "tags.*, authors.id as author_id";
            options.joins = "left outer join posts on taggings.taggable_id = posts.id left outer join authors on posts.author_id = authors.id";
            return ActiveRecords.findFirst(Post.class, options);
        }
    }
}