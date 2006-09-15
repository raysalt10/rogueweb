package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.annotations.Serialize;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.annotations.callbacks.BeforeCreate;
import com.aetrion.activerecord.annotations.callbacks.BeforeDestroy;

import java.util.Date;
import java.util.List;

/**
 * Class representing a Topic
 */
public class Topic extends ActiveRecord {

    @HasMany(foreignKey = "parent_id")
    private List<Reply> replies;

    private String title;
    private String authorName;
    private String authorEmailAddress;
    private Date writtenOn;
    private Date lastRead;
    private Date bonusTime;
    private Object parentId;

    @Serialize
    private String content;

    private boolean approved;
    private int repliesCount;

    // accessor methods

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmailAddress() {
        return authorEmailAddress;
    }

    public void setAuthorEmailAddress(String authorEmailAddress) {
        this.authorEmailAddress = authorEmailAddress;
    }

    public Date getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(Date writtenOn) {
        this.writtenOn = writtenOn;
    }

    public Date getLastRead() {
        return lastRead;
    }

    public void setLastRead(Date lastRead) {
        this.lastRead = lastRead;
    }

    public Date getBonusTime() {
        return bonusTime;
    }

    public void setBonusTime(Date bonusTime) {
        this.bonusTime = bonusTime;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    // callbacks

    @BeforeCreate
    protected void defaultWrittenOn() {
        if (writtenOn == null) writtenOn = new Date();
    }

    @BeforeDestroy
    protected void destroyChildren() {
        ActiveRecords.deleteAll(Topic.class, "parent_id = " + getId());
    }
}
