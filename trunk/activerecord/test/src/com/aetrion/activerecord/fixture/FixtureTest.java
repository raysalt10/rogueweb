package com.aetrion.activerecord.fixture;

import junit.framework.TestCase;

/**
 * 
 */
public class FixtureTest extends TestCase {

    public void testAccountsFixture() {
        Accounts accounts = new Accounts();

        assertEquals(50.0, accounts.aetrion.getCreditLimit());
        assertEquals("Aetrion LLC", accounts.aetrion.getFirm().getName());

        assertEquals(50.0, accounts.rogueCoreAccount.getCreditLimit());
        assertEquals("Rogue Core", accounts.rogueCoreAccount.getFirm().getName());

        assertEquals(55.0, accounts.rogueCoreAccount2.getCreditLimit());
        assertEquals("Rogue Core", accounts.rogueCoreAccount2.getFirm().getName());
    }

    public void testAuthorsFixture() {
        Authors authors = new Authors();
        AuthorFavorites authorFavorites = new AuthorFavorites();

        assertEquals("Anthony", authors.anthony.getName());
        assertNull(authors.anthony.getAuthorAddress());
        assertNotNull(authors.anthony.getAuthorFavorites());
        assertEquals(1, authors.anthony.getAuthorFavorites().size());
        assertNotNull(authors.anthony.getCategories());
        assertEquals(1, authors.anthony.getCategories().size());
    }

}
