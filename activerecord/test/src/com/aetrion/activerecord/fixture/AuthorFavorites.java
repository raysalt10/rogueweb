package com.aetrion.activerecord.fixture;

import com.aetrion.activerecord.models.AuthorFavorite;

/**
 * 
 */
public class AuthorFavorites extends Fixture {

    public AuthorFavorite anthonyMary;

    public void initialize() {
        anthonyMary = new AuthorFavorite();
        anthonyMary.setId(1);
        anthonyMary.setAuthorId(1);
        anthonyMary.setFavoriteAuthorId(2);
    }
}
