package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;

/**
 * 
 */
public class SillyReply extends Reply {

    @BelongsTo(foreignKey = "parent_id", counterCacheName = "replies_count")
    private Reply reply;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }
}
