package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.Dependency;

import java.util.Collection;

/**
 * 
 */
public class DependentFirm extends Firm {

    @HasOne(foreignKey = "firm_id", dependent = Dependency.NULLIFY)
    private Account account;

    @HasMany(foreignKey = "client_of", order = "id", dependent = Dependency.NULLIFY)
    private Collection<Company> companies;

    // accessors

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Collection<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Collection<Company> companies) {
        this.companies = companies;
    }
}
