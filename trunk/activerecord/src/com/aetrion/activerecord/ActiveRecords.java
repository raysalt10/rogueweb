package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.Row;
import com.aetrion.activerecord.errors.ActiveRecordException;
import com.aetrion.activesupport.Strings;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

/**
 * Class for working with multiple ActiveRecord objects.
 * @author Anthony Eden
 */
public class ActiveRecords {

    private ActiveRecords() {

    }

    /**
     * Find ActiveRecord instances based on the find type.
     * @param findType The FindType
     * @param c The Class
     * @param options The options
     * @return The List of AR objects
     */
    public static List<? extends ActiveRecord> find(FindType findType, Class c, AROptions options) {
        switch (findType) {
            case ALL:
                return findAll(c, options);
            case FIRST:
                List<ActiveRecord> collection = new ArrayList<ActiveRecord>();
                collection.add(findFirst(c, options));
                return collection;
            default:
                throw new IllegalArgumentException("Unsupported find type: " + findType);
        }
    }

    public static ActiveRecord findFirst(Class c) {
        return findFirst(c, null);
    }

    public static ActiveRecord findFirst(Class c, AROptions options) {
        Adapter adapter = Adapter.getDefaultAdapter();
        String tableName = tableize(c);

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(tableName);
        List parameters = adapter.addOptions(sql, options);

        Row row = adapter.selectOne(sql.toString(), parameters);
        if (row == null) return null;
        return buildActiveRecord(c, row);
    }

    /**
     * Find all records with the given options.
     * @param c The class
     * @param options The AROptions
     * @return A List of ActiveRecord objects
     */
    public static List<? extends ActiveRecord> findAll(Class c, AROptions options) {
        Adapter adapter = Adapter.getDefaultAdapter();
        String tableName = tableize(c);
        List<ActiveRecord> records = new ArrayList<ActiveRecord>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(tableName);
        List parameters = adapter.addOptions(sql, options);
        System.out.println(parameters.size() + " parameters after addOptions()");

        for (Row row : adapter.selectAll(sql.toString(), parameters)) {
            records.add(buildActiveRecord(c, row));
        }

        return records;
    }

    /**
     * Destroy all of the records associated with the specified class.
     * @param c The class
     * @param options The AROptions
     * @return The number of destroyed records
     */
    public static Integer destroyAll(Class c, AROptions options) {
        int destroyedCount = 0;
        for (ActiveRecord o : findAll(c, options)) {
            o.destroy();
            destroyedCount++;
        }
        return destroyedCount;
    }

    /**
     * Delete all records associated with the specified class.
     * @param c The class
     * @param options AROptions
     * @return The number of deleted records
     */
    public static Integer deleteAll(Class c, AROptions options) {
        Adapter adapter = Adapter.getDefaultAdapter();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(tableize(c));
        // TODO add options
        return (Integer) adapter.execute(sql.toString());
    }

    /**
     * Delete all records associated with the specified class which match the given conditions.
     * @param c The class
     * @param conditions The conditions
     * @return The number of deleted records
     */
    public static Integer deleteAll(Class c, String conditions) {
        AROptions options = new AROptions();
        options.addConditions(conditions);
        return deleteAll(c, options);
    }

    /**
     * Goes down through the class hierarchy until it locates a direct decendant from ActiveRecord and then uses that
     * class for the table name.
     * @param recordClass The starting record class
     * @return The table name
     */
    public static String tableize(Class recordClass) {
        return Strings.tableize(findActiveRecordBaseName(recordClass));
    }

    /**
     *
     * @param recordClass The record class
     * @return The foriegn key using the specified class
     */
    public static String foriegnKey(Class recordClass) {
        return Strings.foreignKey(recordClass.getName());
    }

    public static String foriegnKey(Type type) {
        return foriegnKey((Class) type);
    }

    public static Class findActiveRecordBase(Class recordClass) {
        Class parentClass = recordClass.getSuperclass();
        while (parentClass != ActiveRecord.class) {
            recordClass = parentClass;
            parentClass = recordClass.getSuperclass();
        }
        return recordClass;
    }

    public static String findActiveRecordBaseName(Class recordClass) {
        return findActiveRecordBase(recordClass).getName();
    }

    /**
     * Build an ActiveRecord instance for the given class and fill it with the given row data.
     * @param c The Class
     * @param row The Row
     * @return The ActiveRecord instance
     */
    static ActiveRecord buildActiveRecord(Class c, Row row) {
        try {
            ActiveRecord activeRecord = (ActiveRecord) c.newInstance();
            activeRecord.fill(row);
            return activeRecord;
        } catch (InstantiationException e) {
            throw new ActiveRecordException("Failed to instantiate " + c);
        } catch (IllegalAccessException e) {
            throw new ActiveRecordException("Constructor in " + c + " is not accessible");
        }
    }

}
