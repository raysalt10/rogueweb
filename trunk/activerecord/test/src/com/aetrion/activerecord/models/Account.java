package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.ActiveRecord;

/**
 * Account.
 *
 * @author Anthony Eden
 */
public class Account extends ActiveRecord {

    @BelongsTo
    private Firm firm;

    private Object firmId;

    private Double creditLimit;

    // accessor methods

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Object getFirmId() {
        return firmId;
    }

    public void setFirmId(Object firmId) {
        this.firmId = firmId;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    // validation

    protected void validate() {
        errors.addOnEmpty("credit_limit");
    }
}