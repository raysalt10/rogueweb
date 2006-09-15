package com.aetrion.activerecord.migration.migration_test;

import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.migration.Migration;

/**
 * A test migration which creates a "pets" table.
 *
 * @author Anthony Eden
 */
public class _1_CreateTable extends Migration {

    public void up() {
        createTable("pets", new Migration.TableCallback() {
            public void execute(TableDefinition table) {
                table.column("name", ColumnType.STRING);
            }
        });
    }

    public void down() {
        dropTable("pets");
    }

}
