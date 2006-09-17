package com.aetrion.activerecord;

import junit.framework.TestCase;
import com.aetrion.activerecord.models.Company;

/**
 * Test other annotations.
 *
 * @author Anthony Eden
 */
public class OtherAnnotationsTest extends TestCase {

    /**
     * Test that the annotation for modifying the sequence works as expected.
     */
    public void testModifiedSequence() {
        Company company = new Company();
        assertEquals("companies_nonstd_seq", company.getSequenceName());
    }
}
