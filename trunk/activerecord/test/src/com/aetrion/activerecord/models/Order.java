package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.associations.BelongsTo;

/**
 * 
 */
public class Order extends ActiveRecord {

    @BelongsTo(foreignKey = "billing_customer_id")
    private Customer billing;

    @BelongsTo(foreignKey = "shipping_customer_id")
    private Customer shipping;

    public Customer getBilling() {
        return billing;
    }

    public void setBilling(Customer billing) {
        this.billing = billing;
    }

    public Customer getShipping() {
        return shipping;
    }

    public void setShipping(Customer shipping) {
        this.shipping = shipping;
    }

}
