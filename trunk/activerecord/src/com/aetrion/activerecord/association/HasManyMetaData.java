package com.aetrion.activerecord.association;

import com.aetrion.activerecord.ActiveRecord;
import com.aetrion.activerecord.ActiveRecords;
import com.aetrion.activerecord.AROptions;
import com.aetrion.activerecord.annotations.associations.HasMany;

/**
 * Wrapper for the @HasMany annotation which provides caching of values and specialized logic for each value.
 *
 * @author Anthony
 */
public class HasManyMetaData {

    private ActiveRecord record;
    private HasMany annotation;

    private Class associationClass;
    private String tableName;
    private String primaryKeyName;
    private String associationForeignKey;
    private String select;
    private Class through;
    private String throughFullyQualifiedFieldName;
    private String as;
    private String asFullyQualifiedFieldName;
    private String asTypeFullyQualifiedFieldName;

    public HasManyMetaData(ActiveRecord record, HasMany annotation) {
        this.record = record;
        this.annotation = annotation;
    }

    public Class getAssociationClass() {
        if (associationClass == null) associationClass = ActiveRecords.findActiveRecordBase(record.getClass());
        return associationClass;
    }

    public String getTableName() {
        if (tableName == null) tableName = ActiveRecords.tableize(getAssociationClass());
        return tableName;
    }

    public String getPrimaryKeyName() {
        if (primaryKeyName == null) {
            String foreignKey = null;
            String as = null;

            if (!annotation.foreignKey().equals("")) foreignKey = annotation.foreignKey();
            if (!annotation.as().equals("")) as = annotation.as();

            if (foreignKey != null) {
                primaryKeyName = foreignKey;
            } else if (as != null) {
                primaryKeyName = as + "_id";
            } else {
                primaryKeyName = ActiveRecords.foriegnKey(record.getClass());
            }
        }
        return primaryKeyName;
    }

    public String getAssociationForeignKey() {
        if (associationForeignKey == null) {
            String foreignKey = null;
            if (!annotation.foreignKey().equals("")) foreignKey = annotation.foreignKey();
            if (foreignKey != null) {
                associationForeignKey = foreignKey;
            } else {
                associationForeignKey = ActiveRecords.foriegnKey(getAssociationClass());
            }
        }
        return associationForeignKey;
    }

    public Class getThrough() {
        if (annotation.through().length > 0) through = annotation.through()[0];
        return through;
    }

    public String getThroughFullyQualifiedFieldName() {
        Class through = getThrough();
        if (throughFullyQualifiedFieldName == null && through != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(ActiveRecords.tableize(through));
            sb.append(".");
            sb.append(getPrimaryKeyName());
            throughFullyQualifiedFieldName = sb.toString();
        }
        return throughFullyQualifiedFieldName;
    }

    public String getAs() {
        if (!annotation.as().equals("")) as = annotation.as();
        return as;
    }

    public String getAsFullyQualitifedFieldName() {
        String as = getAs();
        if (asFullyQualifiedFieldName == null & as != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTableName());
            sb.append(".");
            sb.append(getAs());
            sb.append("_id");
            asFullyQualifiedFieldName = sb.toString();
        }
        return asFullyQualifiedFieldName;
    }

    public String getAsTypeFullyQualifiedFieldName() {
        String as = getAs();
        if (asTypeFullyQualifiedFieldName == null && as != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(getTableName());
            sb.append(".");
            sb.append(getAs());
            sb.append("_type");
            asTypeFullyQualifiedFieldName = sb.toString();
        }
        return asTypeFullyQualifiedFieldName;
    }

    public String getSelect() {
        if (select == null) {
            if (!annotation.select().equals("")) {
                select = annotation.select();
            } else {
                select = getTableName() + ".*";
            }
        }
        return select;
    }

    public void addHasManyThroughOptions(AROptions options) {
        // construct from
        options.from = getTableName();

        // construct select
        options.select = getSelect();

        // construct joins
        options.joins = constructJoins();

        // construct conditions
        if (getAs() != null) {
            options.addConditions(getAsFullyQualitifedFieldName() + " = ?",
                    String.valueOf(record.getId()));
            options.addConditions(getAsTypeFullyQualifiedFieldName() + " = ?",
                    ActiveRecords.findActiveRecordBaseName(record.getClass()));
        } else {
            options.addConditions(getThroughFullyQualifiedFieldName() + " = ?",
                    String.valueOf(record.getId()));
        }
    }

    String constructJoins() {
        String metaDataPrimaryKey = null;
        String sourcePrimaryKey = null;
        String polymorphicJoin = null;
        if (getAs() != null) {
            metaDataPrimaryKey = "";
            sourcePrimaryKey = record.getPrimaryKey();
        } else {
            metaDataPrimaryKey = "";
            sourcePrimaryKey = "";

        }
        StringBuilder sql = new StringBuilder();
        sql.append("INNER JOIN ");
        sql.append(getTableName());
        sql.append(" ON ");
        sql.append(getAsFullyQualitifedFieldName());
        sql.append(" = ");
        sql.append(getTableName());
        sql.append(".");
        sql.append(sourcePrimaryKey);

        if (polymorphicJoin != null) {
            sql.append(" ");
            sql.append(polymorphicJoin);
            sql.append(" ");
        }

        return sql.toString();
    }

//    polymorphic_join = nil
//          if @reflection.through_reflection.options[:as] || @reflection.source_reflection.macro == :belongs_to
//            reflection_primary_key = @reflection.klass.primary_key
//            source_primary_key     = @reflection.source_reflection.primary_key_name
//          else
//            reflection_primary_key = @reflection.source_reflection.primary_key_name
//            source_primary_key     = @reflection.klass.primary_key
//            if @reflection.source_reflection.options[:as]
//              polymorphic_join = "AND %s.%s = %s" % [
//                @reflection.table_name, "#{@reflection.source_reflection.options[:as]}_type",
//                @owner.class.quote(@reflection.through_reflection.klass.name)
//              ]
//            end
//          end
//
//          "INNER JOIN %s ON %s.%s = %s.%s %s #{@reflection.options[:joins]} #{custom_joins}" % [
//            @reflection.through_reflection.table_name,
//            @reflection.table_name, reflection_primary_key,
//            @reflection.through_reflection.table_name, source_primary_key,
//            polymorphic_join
//          ]
}
