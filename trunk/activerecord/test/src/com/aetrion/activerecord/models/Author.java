package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.ActiveRecord;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Class representing an author.
 *
 * @author Anthony Eden
 */
public class Author extends ActiveRecord {

    private String name;

    @HasMany
    private List<Post> posts;

    @HasMany(include = Comment.class)
    private List<Post> postsWithComments;

    @HasMany(include = Category.class)
    private List<Post> postsWithCategories;

    @HasMany(include = {Comment.class, Category.class}, order = "posts.id")
    private List<Post> postsWithCategoriesAndComments;

    @HasMany(through = Post.class)
    private List<Comment> comments;

    @HasMany
    private List<Post> specialPosts;

    @HasMany(conditions = "posts.body = 'hello'")
    private List<Post> helloPosts;

    @HasMany(conditions = "posts.body = 'nonexistent'")
    private List<Post> nonExistentPosts;

    @HasMany(beforeAdd = "logBeforeAdding", afterAdd = "logAfterAdding", beforeRemove = "logBeforeRemoving",
            afterRemove = "logAfterRemoving")
    private List<Post> postsWithCallbacks;

    @HasMany(beforeAdd = "raiseException", afterAdd = "logAfterAdding")
    private List<Post> unchangablePosts;

    @HasMany
    private List<Categorization> categorizations;

    @HasMany(through = Categorization.class)
    private List<Category> categories;

    @HasMany(through = Categorization.class, source = Post.class)
    private List<Post> categorizedPosts;

    @HasMany(through = Categorization.class, source = Post.class, unique = true)
    private List<Post> uniqueCategorizedPosts;

    @HasMany
    private List<AuthorFavorite> authorFavorites;

    @HasMany(through = AuthorFavorite.class, order = "name")
    private List<Author> favoriteAuthors;

    private List<String> tagging;
    private List<String> taggings;
    private List<String> tags;

    @HasMany(through = Post.class, source = Category.class)
    private List<Category> postCategories;

    @BelongsTo
    private AuthorAddress authorAddress;

    private Object authorAddressId;

    private List<String> postLog;

    // accessors
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPostsWithComments() {
        return postsWithComments;
    }

    public void setPostsWithComments(List<Post> postsWithComments) {
        this.postsWithComments = postsWithComments;
    }

    public List<Post> getPostsWithCategories() {
        return postsWithCategories;
    }

    public void setPostsWithCategories(List<Post> postsWithCategories) {
        this.postsWithCategories = postsWithCategories;
    }

    public List<Post> getPostsWithCategoriesAndComments() {
        return postsWithCategoriesAndComments;
    }

    public void setPostsWithCategoriesAndComments(List<Post> postsWithCategoriesAndComments) {
        this.postsWithCategoriesAndComments = postsWithCategoriesAndComments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Post> getSpecialPosts() {
        return specialPosts;
    }

    public void setSpecialPosts(List<Post> specialPosts) {
        this.specialPosts = specialPosts;
    }

    public List<Post> getHelloPosts() {
        return helloPosts;
    }

    public void setHelloPosts(List<Post> helloPosts) {
        this.helloPosts = helloPosts;
    }

    public List<Post> getNonExistentPosts() {
        return nonExistentPosts;
    }

    public void setNonExistentPosts(List<Post> nonExistentPosts) {
        this.nonExistentPosts = nonExistentPosts;
    }

    public List<Post> getPostsWithCallbacks() {
        return postsWithCallbacks;
    }

    public void setPostsWithCallbacks(List<Post> postsWithCallbacks) {
        this.postsWithCallbacks = postsWithCallbacks;
    }

    public List<Post> getUnchangablePosts() {
        return unchangablePosts;
    }

    public void setUnchangablePosts(List<Post> unchangablePosts) {
        this.unchangablePosts = unchangablePosts;
    }

    public List<Categorization> getCategorizations() {
        return categorizations;
    }

    public void setCategorizations(List<Categorization> categorizations) {
        this.categorizations = categorizations;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Post> getCategorizedPosts() {
        return categorizedPosts;
    }

    public void setCategorizedPosts(List<Post> categorizedPosts) {
        this.categorizedPosts = categorizedPosts;
    }

    public List<Post> getUniqueCategorizedPosts() {
        return uniqueCategorizedPosts;
    }

    public void setUniqueCategorizedPosts(List<Post> uniqueCategorizedPosts) {
        this.uniqueCategorizedPosts = uniqueCategorizedPosts;
    }

    public Collection<AuthorFavorite> getAuthorFavorites() {
        return authorFavorites;
    }

    public void setAuthorFavorites(List<AuthorFavorite> authorFavorites) {
        this.authorFavorites = authorFavorites;
    }

    public List<Author> getFavoriteAuthors() {
        return favoriteAuthors;
    }

    public void setFavoriteAuthors(List<Author> favoriteAuthors) {
        this.favoriteAuthors = favoriteAuthors;
    }

    public List<String> getTagging() {
        return tagging;
    }

    public void setTagging(List<String> tagging) {
        this.tagging = tagging;
    }

    public List<String> getTaggings() {
        return taggings;
    }

    public void setTaggings(List<String> taggings) {
        this.taggings = taggings;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Category> getPostCategories() {
        return postCategories;
    }

    public void setPostCategories(List<Category> postCategories) {
        this.postCategories = postCategories;
    }

    public AuthorAddress getAuthorAddress() {
        return authorAddress;
    }

    public void setAuthorAddress(AuthorAddress authorAddress) {
        this.authorAddress = authorAddress;
    }

    public Object getAuthorAddressId() {
        return authorAddressId;
    }

    public void setAuthorAddressId(Object authorAddressId) {
        this.authorAddressId = authorAddressId;
    }

    // lifecycle methods

    protected void afterInitialize() {
        postLog = new ArrayList<String>();
    }

    // private methods

    private void logBeforeAdding(Object object) {
        postLog.add("before_adding" + object);
    }

    private void logAfterAdding(Object object) {
        postLog.add("after_adding" + object);
    }

    private void logBeforeRemoving(Object object) {
        postLog.add("before_removing" + object);
    }

    private void logAfterRemoving(Object object) {
        postLog.add("before_removing" + object);
    }

    private void raiseException(Object object) {
        throw new RuntimeException("You can't add a post");
    }

}
