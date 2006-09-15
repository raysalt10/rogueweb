package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.errors.RecordNotFoundException;
import com.aetrion.activerecord.fixture.Topics;
import com.aetrion.activerecord.models.AuthorFavorite;
import com.aetrion.activerecord.models.Post;
import com.aetrion.activerecord.models.Reply;
import com.aetrion.activerecord.models.Topic;
import junit.framework.TestCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Test the basic functionality of the ActiveRecord class.
 */
public class ActiveRecordTest extends TestCase {

    Topics topics;

    public void setUp() {
        topics = new Topics();
    }

    public void testTableName() {
        assertEquals("author_favorites", new AuthorFavorite().getTableName());
        assertEquals("topics", new Topic().getTableName());
        assertEquals("topics", new Reply().getTableName());
    }

    public void testPrimaryKey() {
        assertEquals("id", new Topic().getPrimaryKey());
        Post post = new Post();
        post.setPrimaryKey("post_id");
        assertEquals("post_id", post.getPrimaryKey());
    }

    public void testColumns() {
        Topic topic = new Topic();
        List<Column> columns = topic.getColumns();
        assertEquals(12, columns.size());

        assertEquals("id", columns.get(0).getName());
        assertEquals(ColumnType.INTEGER,  columns.get(0).getType());

        assertEquals("title", columns.get(1).getName());
        assertEquals(ColumnType.STRING, columns.get(1).getType());

        assertEquals("written_on", columns.get(4).getName());
        assertEquals(ColumnType.DATETIME, columns.get(4).getType());

        assertEquals("approved", columns.get(8).getName());
        assertEquals(ColumnType.BOOLEAN, columns.get(8).getType());
    }

    public void testAttributes() {
        Topic topic = new Topic();
        topic.setTitle("Test Topic");
        assertEquals("Test Topic", topic.attribute("title"));
    }

    public void testCreate() {
        Topic topic = new Topic();
        topic.setTitle("New Topic");
        topic.save();

        Topic topicReloaded = new Topic();
        topicReloaded.read(topic.getId());
        assertEquals("New Topic", topicReloaded.getTitle());
    }

    public void testRead() {
        Topic topic = new Topic();
        topic.read(topics.first.getId());
        assertEquals(topics.first.getAuthorName(), topic.getAuthorName());
    }

    public void testSave() {
        Topic topic = new Topic();
        topic.read(topics.first.getId());
        topic.setTitle("Budget");
        topic.setAuthorName("Jason");
        topic.save();

        assertEquals("Budget", topic.getTitle());
        assertEquals("Jason", topic.getAuthorName());
        assertEquals(topics.first.getAuthorEmailAddress(), topic.getAuthorEmailAddress());

        topic.read(topics.first.getId());
        assertEquals("Budget", topic.getTitle());
        assertEquals("Jason", topic.getAuthorName());
        assertEquals(topics.first.getAuthorEmailAddress(), topic.getAuthorEmailAddress());
    }

    public void testDestroy() {
        Topic topic = new Topic();
        topic.read(topics.first.getId());
        topic.destroy();

        try {
            topic.read(topics.first.getId());
            fail("Reading after destroy should have thrown an exception");
        } catch (RecordNotFoundException e) {
            // this is correct
        }
    }

    public void testAnnotation() throws NoSuchFieldException {
        Field f = Topic.class.getDeclaredField("replies");
        Annotation a = f.getAnnotation(HasMany.class);
        assertNotNull("HasMany annotation expected but not found", a);
    }

}