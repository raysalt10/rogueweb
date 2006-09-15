package com.aetrion.activerecord.migrations_with_decimal;

import com.aetrion.activerecord.migration.Migration;
import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.AROptions;

/**
 * 
 */
public class _1_GiveMeBigNumbers extends Migration {
    public void up() {
        createTable("big_numbers", new TableCallback() {
            public void execute(TableDefinition table) {
                table.column("bank_balance", ColumnType.DECIMAL, new AROptions("precision", 10, "scale", 2));
                table.column("big_bank_balance", ColumnType.DECIMAL, new AROptions("precision", 15, "scale", 2));
                table.column("world_population", ColumnType.DECIMAL, new AROptions("precision", 10));
                table.column("my_house_population", ColumnType.DECIMAL, new AROptions("precision", 2));
                table.column("value_of_e", ColumnType.DECIMAL);
            }
        });
    }

    public void down() {
        dropTable("bigNumbers");
    }
}