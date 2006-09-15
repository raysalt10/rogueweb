package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.models.Author;

/**
 * Authors fixture.
 *
 * @author Anthony Eden
 */
public class Authors extends Fixture {

    public Author anthony;
    public Author mary;

    public void initialize() {
        anthony = new Author();
        anthony.setId(1);
        anthony.setName("Anthony");

        mary = new Author();
        mary.setId(2);
        mary.setName("Mary");
    }

}
