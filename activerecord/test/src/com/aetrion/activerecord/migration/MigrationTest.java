package com.aetrion.activerecord.migration;

import junit.framework.TestCase;

/**
 * Test the Migration class.
 *
 * @author Anthony Eden
 */
public class MigrationTest extends TestCase {

    public void testFindMaxVersionNumber() {
        Integer maxVersion = Migration.findMaxVersionNumber("migration_test");
        assertNotNull(maxVersion);
        assertEquals(1, (int) maxVersion);
    }

    public void testMigrateUp() {
//        Migration.migrate("migration_test");
    }

    public void testMigrationDown() {
//        Migration.migrate("migration_test", 0);
    }

}
