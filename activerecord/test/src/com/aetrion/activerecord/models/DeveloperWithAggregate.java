package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.Table;
import com.aetrion.activerecord.annotations.aggregations.ComposedOf;

/**
 * 
 */
@Table("developers")
public class DeveloperWithAggregate extends ActiveRecord {

    @ComposedOf(mapping = {"salary", "amount"})
    private DeveloperSalary salary;

    class DeveloperSalary {
        public double amount;
    }
}
