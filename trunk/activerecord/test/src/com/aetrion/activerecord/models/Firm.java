package com.aetrion.activerecord.models;

import com.aetrion.activerecord.Dependency;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.annotations.associations.HasOne;

import java.util.Collection;

/**
 * 
 */
public class Firm extends Company {

    @HasMany(order = "id", dependent = Dependency.DESTROY,
            counterSql = "SELECT COUNT(*) FROM companies WHERE firm_id = 1 AND " +
                    "($QUOTED_TYPE = 'Client' OR $QUOTED_TYPE = 'SpecialClient' OR $QUOTED_TYPE = 'VerySpecialClient' )")
    private Collection<Client> clients;

    @HasMany(order = "id DESC")
    private Collection<Client> clientsSortedDesc;

    @HasMany(order = "id", foreignKey = "client_of")
    private Collection<Client> clientsOfFirm;

    @HasMany(order = "id", foreignKey = "client_of", dependent = Dependency.DESTROY)
    private Collection<Client> dependentClientsOfFirm;

    @HasMany(order = "id", foreignKey = "client_of", dependent = Dependency.DELETE_ALL)
    private Collection<Client> exclusivelyDependentClientsOfFirm;

    @HasMany(limit = 1, order = "id")
    private Collection<Client> limitedClients;

    @HasMany(conditions = "name = 'Microsoft'", order = "id")
    private Collection<Client> clientsLikeMs;

    @HasMany(finderSql = "SELECT * FROM companies WHERE client_of = $id")
    private Collection<Client> clientsUsingSql;

    @HasMany(finderSql = "SELECT * FROM companies WHERE client_of = $id",
             counterSql = "SELECT COUNT(*) FROM companies WHERE client_of = $id")
    private Collection<Client> clientsUsingCounterSql;

    @HasMany(finderSql = "SELECT * FROM companies WHERE client_of = $id",
             counterSql = "SELECT 0 FROM companies WHERE client_of = $id")
    private Collection<Client> clientsUsingZeroCounterSql;

    @HasMany(finderSql = "SELECT * FROM companies WHERE client_of = 1000",
             counterSql = "SELECT COUNT(*) FROM companies WHERE client_of = 1000")
    private Collection<Client> noClientsUsingCounterSql;

    @HasMany
    private Collection<Client> plainClients;

    @HasOne(foreignKey = "firm_id", dependent = Dependency.DESTROY)
    private Account account;

    // accessor methods

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    public Collection<Client> getClientsSortedDesc() {
        return clientsSortedDesc;
    }

    public void setClientsSortedDesc(Collection<Client> clientsSortedDesc) {
        this.clientsSortedDesc = clientsSortedDesc;
    }

    public Collection<Client> getClientsOfFirm() {
        return clientsOfFirm;
    }

    public void setClientsOfFirm(Collection<Client> clientsOfFirm) {
        this.clientsOfFirm = clientsOfFirm;
    }

    public Collection<Client> getDependentClientsOfFirm() {
        return dependentClientsOfFirm;
    }

    public void setDependentClientsOfFirm(Collection<Client> dependentClientsOfFirm) {
        this.dependentClientsOfFirm = dependentClientsOfFirm;
    }

    public Collection<Client> getExclusivelyDependentClientsOfFirm() {
        return exclusivelyDependentClientsOfFirm;
    }

    public void setExclusivelyDependentClientsOfFirm(Collection<Client> exclusivelyDependentClientsOfFirm) {
        this.exclusivelyDependentClientsOfFirm = exclusivelyDependentClientsOfFirm;
    }

    public Collection<Client> getLimitedClients() {
        return limitedClients;
    }

    public void setLimitedClients(Collection<Client> limitedClients) {
        this.limitedClients = limitedClients;
    }

    public Collection<Client> getClientsLikeMs() {
        return clientsLikeMs;
    }

    public void setClientsLikeMs(Collection<Client> clientsLikeMs) {
        this.clientsLikeMs = clientsLikeMs;
    }

    public Collection<Client> getClientsUsingSql() {
        return clientsUsingSql;
    }

    public void setClientsUsingSql(Collection<Client> clientsUsingSql) {
        this.clientsUsingSql = clientsUsingSql;
    }

    public Collection<Client> getClientsUsingCounterSql() {
        return clientsUsingCounterSql;
    }

    public void setClientsUsingCounterSql(Collection<Client> clientsUsingCounterSql) {
        this.clientsUsingCounterSql = clientsUsingCounterSql;
    }

    public Collection<Client> getClientsUsingZeroCounterSql() {
        return clientsUsingZeroCounterSql;
    }

    public void setClientsUsingZeroCounterSql(Collection<Client> clientsUsingZeroCounterSql) {
        this.clientsUsingZeroCounterSql = clientsUsingZeroCounterSql;
    }

    public Collection<Client> getNoClientsUsingCounterSql() {
        return noClientsUsingCounterSql;
    }

    public void setNoClientsUsingCounterSql(Collection<Client> noClientsUsingCounterSql) {
        this.noClientsUsingCounterSql = noClientsUsingCounterSql;
    }

    public Collection<Client> getPlainClients() {
        return plainClients;
    }

    public void setPlainClients(Collection<Client> plainClients) {
        this.plainClients = plainClients;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
