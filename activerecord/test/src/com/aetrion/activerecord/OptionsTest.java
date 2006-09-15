package com.aetrion.activerecord;

import junit.framework.TestCase;
import org.junit.Test;
import com.aetrion.activerecord.errors.InvalidOptionException;
import com.aetrion.activerecord.adapter.Conditions;

import java.util.List;
import java.util.ArrayList;

/**
 * Test the AROptions class.
 *
 * @author Anthony Eden
 */
public class OptionsTest extends TestCase {

    @Test public void testOptionsConstructor() {
        List<Conditions> conditions = new ArrayList<Conditions>();
        conditions.add(new Conditions("x = ?", "1"));
        AROptions options = new AROptions("order", "id", "conditions", conditions);
        assertEquals("id", options.order);
        assertEquals(conditions, options.conditions);
    }

    @Test(expected = InvalidOptionException.class)
    public void testNoSuchFieldExceptionFromConstructor() {
//        new AROptions("foo", "bar");
    }

}
