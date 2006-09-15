package com.aetrion.activerecord.migrations;

import com.aetrion.activerecord.migration.Migration;
import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.adapter.ColumnType;

/**
 * 
 */
public class _2_WeNeedReminders extends Migration {
    public void up() {
        createTable("reminders", new Migration.TableCallback() {
            public void execute(TableDefinition table) {
                table.column("content", ColumnType.TEXT);
                table.column("remind_at", ColumnType.DATETIME);
            }
        });
    }

    public void down() {
        dropTable("reminders");
    }
}