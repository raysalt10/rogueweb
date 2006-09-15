package com.aetrion.activerecord.migrations;

import com.aetrion.activerecord.migration.Migration;

import static com.aetrion.activerecord.adapter.ColumnType.*;

/**
 * 
 */
public class _1_PeopleHaveLastNames extends Migration {

    public void up() {
        addColumn("people", "last_name", STRING);
    }

    public void down() {
        removeColumn("people", "last_name");
    }

}
