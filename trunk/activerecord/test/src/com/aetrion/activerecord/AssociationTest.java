package com.aetrion.activerecord;

import com.aetrion.activerecord.fixture.Accounts;
import com.aetrion.activerecord.fixture.Topics;
import com.aetrion.activerecord.fixture.Companies;
import com.aetrion.activerecord.models.Topic;
import com.aetrion.activerecord.models.Firm;
import junit.framework.TestCase;

import java.util.List;

/**
 * Test associations.
 *
 * @author Anthony Eden
 */
public class AssociationTest extends TestCase {

    private Topics topics;
    private Companies companies;
    private Accounts accounts;

    public void setUp() {
        topics = new Topics();
        companies = new Companies();
        accounts = new Accounts();
    }

    /**
     * Test the HasMany association.
     */
    public void testHasMany() {
        Topic topic = new Topic();
        topic.read(topics.first.getId());
        assertNotNull("Topic.replies is null when it shouldn't have been", topic.getReplies());

        List replies = topic.getReplies();
        assertNotNull("Iterator from Topic.replies is null when it shouldn't have been", replies);

        assertEquals(1, replies.size());
    }

    public void testHasOne() {
        
    }

    public void testBelongsTo() {
        Firm firm = accounts.aetrion.getFirm();
        assertNotNull("Firm from Aetrion was null when is shouldn't have been", firm);
        assertEquals(1, firm.getId());

        firm = accounts.rogueCoreAccount.getFirm();
        assertNotNull("Firm from rogueCoreAccount was null when it shouldn't have been", firm);
        assertEquals(6, firm.getId());
    }
}
