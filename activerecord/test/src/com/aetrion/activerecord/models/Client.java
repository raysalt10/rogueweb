package com.aetrion.activerecord.models;

import com.aetrion.activerecord.annotations.associations.BelongsTo;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * 
 */
public class Client extends Company {
    @BelongsTo(foreignKey = "client_of")
    private Firm firm;

    @BelongsTo(foreignKey = "firm_id")
    private Firm firmWithBasicId;

    @BelongsTo(foreignKey = "client_of")
    private Firm firmWithOtherName;

    @BelongsTo(foreignKey = "client_of", conditions = {"1 = ?", "1"})
    private Firm firmWithCondition;

    /*
      Record destruction so we can test whether firm.clients.clear has
      is calling client.destroy, deleting from the database, or setting
      foreign keys to NULL.
    */
    private Map<Object, List<Object>> destroyedClientIds = new HashMap<Object, List<Object>>();

    // accessor methods

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Firm getFirmWithBasicId() {
        return firmWithBasicId;
    }

    public void setFirmWithBasicId(Firm firmWithBasicId) {
        this.firmWithBasicId = firmWithBasicId;
    }

    public Firm getFirmWithOtherName() {
        return firmWithOtherName;
    }

    public void setFirmWithOtherName(Firm firmWithOtherName) {
        this.firmWithOtherName = firmWithOtherName;
    }

    public Firm getFirmWithCondition() {
        return firmWithCondition;
    }

    public void setFirmWithCondition(Firm firmWithCondition) {
        this.firmWithCondition = firmWithCondition;
    }

    public Map<Object, List<Object>> getDestroyedClientIds() {
        return destroyedClientIds;
    }

    // callbacks

    protected boolean beforeDestroy(Object object) {
        Client client = (Client) object;
        if (client.getFirm() != null) {
            Map<Object, List<Object>> destroyedClientIds = getDestroyedClientIds();
            List<Object> clientIds = destroyedClientIds.get(client.getFirm().getId());
            if (clientIds == null) {
                clientIds = new ArrayList<Object>();
                destroyedClientIds.put(client.getFirm().getId(), clientIds);
            }
            clientIds.add(client.getId());
        }
        return true;
    }
}