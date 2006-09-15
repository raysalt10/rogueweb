package com.aetrion.activerecord;

import com.aetrion.activerecord.models.Topic;
import com.aetrion.activerecord.fixture.Topics;
import junit.framework.TestCase;

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

    public void testFindOne() {
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
