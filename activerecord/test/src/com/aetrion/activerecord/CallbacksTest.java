package com.aetrion.activerecord;

import com.aetrion.activerecord.models.Topic;
import junit.framework.TestCase;

/**
 * Test callbacks.
 *
 * @author Anthony Eden
 */
public class CallbacksTest extends TestCase {

    /**
     * Test the BeforeCreate annotation.
     */
    public void testBeforeCreate() {
        Topic topic = new Topic();
        topic.save();
        assertNotNull(topic.getWrittenOn());
    }

}
