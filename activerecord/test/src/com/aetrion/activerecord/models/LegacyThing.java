package com.aetrion.activerecord.models;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.annotations.LockingColumn;

/**
 * 
 */
@LockingColumn("version")
public class LegacyThing extends ActiveRecord {

    private Integer tpsReportNumber;

    public Integer getTpsReportNumber() {
        return tpsReportNumber;
    }

    public void setTpsReportNumber(Integer tpsReportNumber) {
        this.tpsReportNumber = tpsReportNumber;
    }
}
