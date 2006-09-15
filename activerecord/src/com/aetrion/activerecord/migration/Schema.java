package com.aetrion.activerecord.migration;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activesupport.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public abstract class Schema extends Migration {

    protected abstract Map<String, Object> define();

    public final void up() {
        execute();
    }

    public final void down() {

    }

    public void execute() {
        Map<String, Object> info = define();
        if (info != null) {
            initializeSchemaInformation();
            Adapter adapter = getAdapter();
            Collection<Column> columns = adapter.getColumns(SCHEMA_INFO_TABLE_NAME);
            List<String> infoList = new ArrayList<String>();
            for (Column c : columns) {
                String value = String.valueOf(info.get(c.getName()));
                if (value != null) {
                    infoList.add(c.getName() + " = " + adapter.quote(value));
                }
            }
            adapter.update("UPDATE " + SCHEMA_INFO_TABLE_NAME + " SET " + Strings.join(infoList, ", "));
        }
    }
}
