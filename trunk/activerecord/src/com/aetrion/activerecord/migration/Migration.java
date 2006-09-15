package com.aetrion.activerecord.migration;

import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.ColumnType;
import com.aetrion.activerecord.adapter.TableDefinition;
import com.aetrion.activerecord.errors.MigrationException;
import com.aetrion.activesupport.Classes;
import com.aetrion.activesupport.Strings;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Database migration.
 *
 * <p>Note: inner classes are not supported for migrations at this time.
 * @author Anthony Eden
 */
public abstract class Migration {

    public static String SCHEMA_INFO_TABLE_NAME = "schema_info";

    /** Callback which is used when defining tables. */
    public interface TableCallback {
        void execute(TableDefinition table);
    }

    public static void migrate(String migrationsPackage) {
        migrate(migrationsPackage, null);
    }

    public static void migrate(String migrationsPackage, Integer targetVersion) {
        initializeSchemaInformation();
        int currentVersion = getCurrentVersion();
        if (targetVersion == null || currentVersion < targetVersion) {
            migrateUp(migrationsPackage, targetVersion);
        } else if (currentVersion > targetVersion) {
            migrateDown(migrationsPackage, targetVersion);
        }
    }

    static void migrateUp(String migrationsPackage, Integer versionNumber) {
        if (versionNumber == null) versionNumber = findMaxVersionNumber(migrationsPackage);
        for (int i = getCurrentVersion() + 1; i <= versionNumber; i++) {
            String pattern = "._" + i + "_";
            String pathFilter = Strings.underscore(migrationsPackage);
            Class c = Classes.findClass(pattern, pathFilter);
            if (c != null) {
                try {
                    Migration migration = (Migration) c.newInstance();
                    migration.up();
                    updateSchemaVersion(i);
                } catch (InstantiationException e) {
                    throw new MigrationException("Unable to instantiate new instance of migration " + c);
                } catch (IllegalAccessException e) {
                    throw new MigrationException("Migration " + c + " must have an empty public constructor");
                }
            }
        }
    }

    static void migrateDown(String migrationsPackage, Integer versionNumber) {
        for (int i = getCurrentVersion(); i > versionNumber; i--) {
            String pattern = "._" + i + "_";
            String pathFilter = Strings.underscore(migrationsPackage);
            Class c = Classes.findClass(pattern, pathFilter);
            if (c != null) {
                try {
                    Migration migration = (Migration) c.newInstance();
                    migration.down();
                    updateSchemaVersion(i - 1);
                } catch (InstantiationException e) {
                    throw new MigrationException("Unable to instantiate new instance of migration class " + c);
                } catch (IllegalAccessException e) {
                    throw new MigrationException("Migration " + c + " must have an empty public constructor");
                }
            }
        }
    }

    static Integer findMaxVersionNumber(String migrationsPackage) {
        int maxVersion = 0;
        Pattern p = Pattern.compile("[\\$|\\.]_(\\d)_");
//        System.out.println("path filter: " + migrationsPackage);
        for (String className : Classes.list(migrationsPackage)) {
//            System.out.println("does " + className + " match " + p + "?");
            Matcher m = p.matcher(className);
            if (m.find()) {
                int i = Integer.parseInt(m.group(1));
                if (i > maxVersion) maxVersion = i;
            }
        }
        return maxVersion;
    }

    static void updateSchemaVersion(Integer newSchemaVersion) {
        getAdapter().update("UPDATE " + SCHEMA_INFO_TABLE_NAME + " SET version = " + newSchemaVersion);
    }

    // abstract methods to implement

    /** This method is called to migrate up to the next version. */
    public abstract void up();

    /** This method is called to migrate down to the previous version. */
    public abstract void down();

    protected void addColumn(String tableName, String columnName, ColumnType columnType) {
        addColumn(tableName, columnName, columnType, new AROptions());
    }

    protected void addColumn(String tableName, String columnName, ColumnType columnType, AROptions options) {
        getAdapter().addColumn(tableName, columnName, columnType, options);
    }

    protected void removeColumn(String tableName, String columnName) {
        getAdapter().removeColumn(tableName, columnName);
    }

    protected void createTable(String tableName, TableCallback callback) {
        createTable(tableName, new AROptions(), callback);
    }

    protected void createTable(String tableName, AROptions options, TableCallback callback) {
        getAdapter().createTable(tableName, options, callback);
    }

    protected void dropTable(String tableName) {
        getAdapter().dropTable(tableName);
    }

    protected static Adapter getAdapter() {
        return Adapter.getDefaultAdapter();
    }

    /**
     * Get the current migration version number.
     * @return The current version or 0
     */
    protected static Integer getCurrentVersion() {
        Integer version = (Integer) getAdapter().selectOne(
                "SELECT version FROM " + SCHEMA_INFO_TABLE_NAME).get("version");
        if (version == null) version = 0;
        return version;
    }

    protected static void initializeSchemaInformation() {
        Adapter adapter = getAdapter();
        try {
            adapter.execute("CREATE TABLE " + SCHEMA_INFO_TABLE_NAME + " (version " + adapter.typeToSql(
                    ColumnType.INTEGER) + ")");
            adapter.execute("INSERT INTO " + SCHEMA_INFO_TABLE_NAME + " (version) VALUES(0)");
        } catch (Exception e) {
            // do nothing
        }
    }

}
