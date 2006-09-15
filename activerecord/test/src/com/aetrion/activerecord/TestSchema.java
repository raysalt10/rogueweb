package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.migration.Schema;

import java.util.Map;

/**
 * 
 */
public class TestSchema extends Schema {

    public Map<String,Object> define() {
        createTable("taggings", new AROptions("force", true), new TableCallback() {
            public void execute(TableDefinition t) {
                t.column("tag_id", ColumnType.INTEGER);
                t.column("super_tag_id", ColumnType.INTEGER);
                t.column("taggable_type", ColumnType.STRING);
                t.column("taggable_id", ColumnType.INTEGER);
            }
        });

        createTable("tags", new AROptions("force", true), new TableCallback() {
            public void execute(TableDefinition t) {
                t.column("name", ColumnType.STRING);
                t.column("taggings_count", ColumnType.INTEGER, new AROptions("defaultValue", 0));
            }
        });

        createTable("categorizations", new AROptions("force", true), new TableCallback() {
            public void execute(TableDefinition t) {
                t.column("category_id", ColumnType.INTEGER);
                t.column("post_id", ColumnType.INTEGER);
                t.column("author_id", ColumnType.INTEGER);
            }
        });

        addColumn("posts", "taggings_count", ColumnType.INTEGER, new AROptions("defaultValue", 0));
        addColumn("authors", "author_address_id", ColumnType.INTEGER);

        createTable("author_addresses", new AROptions("force", true), new TableCallback() {
            public void execute(TableDefinition t) {
                t.column("author_address_id", ColumnType.INTEGER);
            }
        });

        createTable("author_favorites", new AROptions("force", true), new TableCallback() {
            public void execute(TableDefinition t) {
                t.column("author_id", ColumnType.INTEGER);
                t.column("favorite_author_id", ColumnType.INTEGER);
            }
        });

        return null; // no options to specify
    }

}

