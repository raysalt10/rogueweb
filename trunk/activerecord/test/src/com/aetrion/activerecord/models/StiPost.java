package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.HasOne;

/**
 * 
 */
public class StiPost {

    @HasOne
    private SpecialComment specialComment;

}
