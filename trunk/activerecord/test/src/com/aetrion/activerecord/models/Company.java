package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.Protected;
import com.aetrion.activerecord.annotations.Sequence;
import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.annotations.validations.ValidatesPresenceOf;
import com.aetrion.activerecord.ActiveRecord;

/**
 * 
 */
@Sequence("companies_nonstd_seq")
@ValidatesPresenceOf({"name"})
public class Company extends ActiveRecord {

    @Protected
    private int rating;

    @HasOne(foreignKey = "firm_id")
    private Account dummyAccount;

    private String name;

    private Object firmId;
    private Object clientOf;

    // accessors

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Account getDummyAccount() {
        return dummyAccount;
    }

    public void setDummyAccount(Account dummyAccount) {
        this.dummyAccount = dummyAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFirmId() {
        return firmId;
    }

    public void setFirmId(Object firmId) {
        this.firmId = firmId;
    }

    public Object getClientOf() {
        return clientOf;
    }

    public void setClientOf(Object clientOf) {
        this.clientOf = clientOf;
    }

    // other methods

    public String arbitraryMethod() {
        return "I am Jack's profound disappointment";
    }

}
