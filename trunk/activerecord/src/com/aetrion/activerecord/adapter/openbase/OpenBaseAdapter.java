package com.aetrion.activerecord.adapter.openbase;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.adapter.NativeType;

import java.util.Collection;
import java.util.Map;
import java.util.List;

/**
 * 
 */
public class OpenBaseAdapter extends Adapter {

    public Collection<Column> getColumns(String tableName) {
        return null;
    }

    public Object execute(String sql, List parameters, String name) {
        return null;
    }

    public Integer delete(String sql, String name) {
        return 0;
    }

    public Map<ColumnType, NativeType> getNativeDatabaseTypes() {
        return null;
    }

    public void beginTransaction() {
    }

    public void commitTransaction() {
    }

    public void rollbackTransaction() {
    }
}
