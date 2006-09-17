package com.aetrion.activerecord;

import junit.framework.TestCase;
import com.aetrion.activerecord.models.Reply;

/**
 * 
 */
public class ValidationsTest extends TestCase {

    public void testValidateOnMethod() {
        Reply reply = new Reply();
        reply.save();
        assertTrue(reply.errors.size() > 0);
    }

}
