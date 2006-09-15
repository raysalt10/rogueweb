package com.aetrion.activerecord.migrations_with_duplicate;

import com.aetrion.activerecord.migration.Migration;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.adapter.TableDefinition;

/**
 * 
 */
public class _3_InnocentJoinTable extends Migration {
    public void up() {
        createTable("people_reminders", new AROptions("id", false), new TableCallback() {
            public void execute(TableDefinition table) {
                table.column("reminder_id", ColumnType.INTEGER);
                table.column("person_id", ColumnType.INTEGER);
            }
        });
    }

    public void down() {
        dropTable("people_reminders");
    }
}