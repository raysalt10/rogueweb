package com.aetrion.activerecord;

import com.aetrion.activerecord.models.Topic;
import com.aetrion.activerecord.fixture.Topics;
import junit.framework.TestCase;

import java.util.List;

/**
 * Test the ActiveRecords class.
 *
 * @author Anthony Eden
 */
public class ActiveRecordsTest extends TestCase {

    private Topics topics;

    public void setUp() {
        topics = new Topics();
    }

    public void testFindFirst() {
        List<? extends ActiveRecord> records = ActiveRecords.find(FindType.FIRST, Topic.class, null);
        assertNotNull(records);
        assertEquals(1, records.size());
        Topic topic = (Topic) records.get(0);
        assertEquals(topics.first.getTitle(), topic.getTitle());
    }

    public void testFindAll() {
        List<? extends ActiveRecord> records = ActiveRecords.find(FindType.ALL, Topic.class, null);
        assertNotNull(records);
        assertEquals(2, records.size());
        for (int i = 0; i < topics.size() ; i++) {
            Topic topic = (Topic) topics.get(i);
            assertEquals(topic.getTitle(), ((Topic) records.get(i)).getTitle());
            // Right now the assertion below fails because the type coming from the fixture is a Long whereas
            // the type coming from AR is an int.
            // TODO: Need to figure out how to deal with this.
            // assertEquals(topic.getId(), records.get(i).getId());
        }
    }

    public void testFindFirstMethod() {
        Topic topic = (Topic) ActiveRecords.findFirst(Topic.class);
        assertNotNull(topic);
        assertEquals(topics.first.getTitle(), topic.getTitle());
    }

    public void testFindOneWithConditions() {
        AROptions options = new AROptions();
        options.addConditions("title = ?", "Example");
        Topic topic = (Topic) ActiveRecords.findFirst(Topic.class, options);
        assertNotNull("Topic should not have been null", topic);
        assertEquals("Example", topic.getTitle());
    }

}
