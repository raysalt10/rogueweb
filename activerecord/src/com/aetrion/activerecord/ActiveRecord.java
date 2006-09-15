package com.aetrion.activerecord;

import com.aetrion.activerecord.adapter.Adapter;
import com.aetrion.activerecord.adapter.Column;
import com.aetrion.activerecord.adapter.Row;
import com.aetrion.activerecord.annotations.PrimaryKey;
import com.aetrion.activerecord.annotations.aggregations.ComposedOf;
import com.aetrion.activerecord.annotations.associations.BelongsTo;
import com.aetrion.activerecord.annotations.associations.HasAndBelongsToMany;
import com.aetrion.activerecord.annotations.associations.HasMany;
import com.aetrion.activerecord.annotations.associations.HasOne;
import com.aetrion.activerecord.annotations.callbacks.*;
import com.aetrion.activerecord.association.AssociationProxy;
import com.aetrion.activerecord.association.HasManyProxy;
import com.aetrion.activerecord.errors.ReadOnlyException;
import com.aetrion.activerecord.errors.RecordNotFoundException;
import com.aetrion.activerecord.errors.IllegalAnnotationException;
import com.aetrion.activerecord.validations.ValidationErrors;
import com.aetrion.activesupport.Classes;
import com.aetrion.activesupport.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Base class all ActiveRecord objects must extend from.
 * @author Anthony Eden
 */
@PrimaryKey("id")
public abstract class ActiveRecord {

    /** Set to true to include the primary key in inserts and updates. */
    public boolean includePrimaryKey = false;

    /** The adapter. */
    protected Adapter adapter;

    /** The table name. */
    protected String tableName;

    /** Validation errors. */
    protected ValidationErrors errors;

    /** Set to true for read only. */
    protected boolean readOnly = false;

    /** Set to false to indicate this is not a new record. */
    protected boolean newRecord = true;

    private Object id;

    private String primaryKey;
    private String sequenceName;

    private List<Column> columns;

    private List<AssociationProxy> associationProxies = new ArrayList<AssociationProxy>();

    // accessors

    /**
     * Get the adapter for this AR object. If no Adapter has been set then {@link Adapter#getDefaultAdapter()} will be
     * used to obtain an adapter.
     * @return The Adapter
     */
    public Adapter getAdapter() {
        if (adapter == null) adapter = Adapter.getDefaultAdapter();
        return adapter;
    }

    /**
     * Set the adapter for this AR object.
     * @param adapter The Adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Get the table name for this AR object. If the table name has not been set then the table name will be determined
     * by walking up the class hierachy until the direct decendent of the ActiveRecord class is found. For example, if
     * your hierarchy was: Employee extends Person extends ActiveRecord then the table name would be "people".
     * @return The table name
     */
    public String getTableName() {
        if (tableName == null) {
            tableName = Strings.tableize(ActiveRecords.findActiveRecordBaseName(getClass()));
        }
        return tableName;
    }

    /**
     * Set the table name for this AR object.
     * @param tableName The table name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Get the validation errors. This method should never return null.
     * @return The validation errors
     */
    public ValidationErrors getErrors() {
        if (errors == null) errors = new ValidationErrors();
        return errors;
    }

    /**
     * Set the validation errors.
     * @param errors The validation errors
     */
    public void setErrors(ValidationErrors errors) {
        this.errors = errors;
    }

    /**
     * Get the primary key value for this record.
     * @return The primary key value
     */
    public Object getId() {
        return id;
    }

    /**
     * Set the primary key value for this record.
     * @param id The primary key value
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * Return true if this record is "read only".
     * @return True if read only
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Set to tue to indicate that this record is "read only".
     * @param readOnly True to indicate read only
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Return true if this record is a new record not yet persisted to the database.
     * @return True if this is a new record
     */
    public boolean isNewRecord() {
        return newRecord;
    }

    /**
     * Get the primary key field name. The default value is "id".
     * @return The primary key field name
     */
    public String getPrimaryKey() {
        if (primaryKey == null) primaryKey = "id";
        return primaryKey;
    }

    /**
     * Set the primary key field name.
     * @param primaryKey The primary key field name
     */
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * Get the sequence name. The default value is null.
     * @return The sequence name
     */
    public String getSequenceName() {
        return sequenceName;
    }

    /**
     * Set the sequence name.
     * @param sequenceName The sequence name
     */
    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    /**
     * Get the columns for this AR object. The AR object caches the columns so they only need to be looked up once from
     * the database.
     * @return The Collection of Column object
     */
    public List<Column> getColumns() {
        if (columns != null) return columns;
        columns = new ArrayList<Column>();
        for (Column column : getAdapter().getColumns(getTableName())) {
            if (column.getName().equals(getPrimaryKey())) {
                column.setPrimary(true);
            }
            columns.add(column);
        }
        return columns;
    }

    // SQL execution methods

    /**
     * Read the object with the specified ID.
     * @param id The id
     */
    public ActiveRecord read(Object id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(getTableName()).append(" WHERE id = ").append(id);
        Row row = getAdapter().selectOne(sql.toString());
        if (row == null) throw new RecordNotFoundException("No record found with id " + id);
        for (Column column : getColumns()) {
            String name = column.getName();
            Classes.setFieldValue(this, name, row.get(name));
        }
        readAssociations();
        readAggregations();
        readCallbacks();
        newRecord = false;
        return this;
    }

    /**
     * Create the AR object and return the object's primary key.
     * @return The primary key
     */
    public Object create() {
        Adapter adapter = getAdapter();
        if (getId() == null && adapter.shouldPrefetchPrimaryKey(getTableName())) {
            this.id = adapter.getNextSequenceValue(getSequenceName());
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(getTableName()).append(" (");
        sql.append(quotedColumnNames(includePrimaryKey));
        sql.append(") VALUES (").append(quotedAttributeValues(includePrimaryKey)).append(")");

        id = adapter.insert(sql.toString(), getClass().getName() + " Create");

        readAssociations();
        readAggregations();
        readCallbacks();

        newRecord = false;

        return id;
    }

    /**
     * Update the AR object in the database.
     * @return True if the object was updated
     */
    public boolean update() {
        Adapter adapter = getAdapter();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(getTableName());
        sql.append(" SET ").append(quotedCommaPairList(attributesWithQuotes(includePrimaryKey)));
        sql.append(" WHERE ").append(getPrimaryKey()).append(" = ").append(adapter.quote(getId()));

        boolean result = adapter.update(sql.toString(), getClass().getName() + " Update") == 1;
        if (result) {
            reloadAssociations();
            reloadAggregations();
            reloadCallbacks();
        }
        return result;
    }

    public boolean updateAttributes(Map<String,Object> attributes) {
        for (Map.Entry<String,Object> entry : attributes.entrySet()) {
            Classes.setFieldValue(this, entry.getKey(), entry.getValue());
        }
        return save();
    }

    /**
     * Create or update depending on whether or not the record is new.
     */
    void createOrUpdate() {
        if (isNewRecord()) {
            create();
        } else {
            update();
        }
    }

    /**
     * Save the current AR object. This will either create or update the record as needed.
     * @return True if the save succeeded
     */
    public boolean save() {
        if (readOnly) throw new ReadOnlyException(getClass().getName() + " is read only");
        createOrUpdate();
        return true;
    }

    /**
     * Destroy the AR object.
     * @return True if the record was destroyed
     */
    public boolean destroy() {
        Adapter adapter = getAdapter();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getTableName());
        sql.append(" WHERE ");
        sql.append(getPrimaryKey());
        sql.append(" = ");
        sql.append(adapter.quote(attribute(getPrimaryKey())));
        return adapter.execute(sql.toString(), getClass().getName() + " Destroy") == new Integer(1);
    }

    /**
     * Get the named attribute
     * @param name The attribute name
     * @return The value
     */
    public Object attribute(String name) {
        return Classes.getFieldValue(this, name);
    }

    /**
     * Return the attributes of the object as a hash map.
     * @return The attributes
     */
    public Map<String, Object> attributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();
        for (Column column : getColumns()) {
            String name = Strings.camelize(column.getName(), true);
            attributes.put(name, attribute(name));
        }
        return attributes;
    }

    /**
     * Return the attributes of the object with the values quoted.
     * @return The attributes with values quoted
     */
    Map<String, ?> attributesWithQuotes() {
        return attributesWithQuotes(true);
    }

    /**
     * Return the attributes of the object with the values quoted.
     * @return The attributes with values quoted
     */
    Map<String, ?> attributesWithQuotes(boolean includePrimaryKey) {
        Adapter adapter = getAdapter();
        Map<String, String> quoted = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : attributes().entrySet()) {
            String name = entry.getKey();
            Column column = getColumnForAttribute(name);
            if (!column.isPrimary() || (column.isPrimary() && includePrimaryKey)) {
                quoted.put(Strings.underscore(name), adapter.quote(entry.getValue(), column));
            }
        }
        return quoted;
    }

    String quotedAttributeValues(boolean includePrimaryKey) {
        Adapter adapter = getAdapter();
        List<String> values = new ArrayList<String>();
        for (Column column : getColumns()) {
            if (!column.isPrimary() || includePrimaryKey) {
                values.add(adapter.quote(attribute(column.getName()), column));
            }
        }
        return Strings.join(values, ", ");
    }

    String quotedColumnNames(boolean includePrimaryKey) {
        List<String> columnNames = new ArrayList<String>();
        for (Column column : getColumns()) {
            if (!column.isPrimary() || includePrimaryKey) {
                columnNames.add(Strings.underscore(column.getName()));
            }
        }
        return Strings.join(columnNames, ", ");
    }

    /**
     * Return a String with all of the attributes in the given map quoted.
     * @param attributes The attributes
     * @return The String
     */
    String quotedCommaPairList(Map<String, ?> attributes) {
        StringBuilder sb = new StringBuilder();
        Iterator entries = attributes.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ?> entry = (Map.Entry<String, ?>) entries.next();
            sb.append(entry.getKey()).append(" = ").append(entry.getValue());
            if (entries.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    Column getColumnForAttribute(String name) {
        name = Strings.underscore(name);
        for (Column column : getColumns()) {
            if (column.getName().equals(name)) return column;
        }
        return null;
    }

    /**
     * Fill the active record object with data from the row.
     * @param row The row
     */
    void fill(Row row) {
        if (row == null) return;
        for (Column column : getColumns()) {
            String name = column.getName();
            Object value = row.get(name);
            Classes.setFieldValue(this, name, value);
        }
    }

    /** Read all associations and construct proxies. */
    void readAssociations() {
        // proxy the collections for lazy loading
        Class c = getClass();
        for (Field f : c.getDeclaredFields()) {
            for (Annotation a : f.getAnnotations()) {
                if (a.annotationType().equals(HasMany.class)) {
                    HasManyProxy proxyHandler = new HasManyProxy(this, f, (HasMany) a);
                    associationProxies.add(proxyHandler);
                    Classes.setFieldValue(this, f.getName(), Proxy.newProxyInstance(List.class.getClassLoader(),
                            new Class[]{List.class}, proxyHandler));
                } else if (a.annotationType().equals(HasAndBelongsToMany.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(HasOne.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(BelongsTo.class)) {
                    BelongsTo belongsTo = (BelongsTo) a;
                    if (!(f.getGenericType() instanceof Class))
                        throw new IllegalAnnotationException("@BelongsTo can only be applied to non-generic fields");
                    ActiveRecord ar = (ActiveRecord) Classes.newInstance(f.getGenericType());
                    String fk = ActiveRecords.foriegnKey(f.getGenericType());
                    if (!belongsTo.foreignKey().equals("")) fk = belongsTo.foreignKey();
                    System.out.println("foriegn key = " + fk);
                    Object fkValue = Classes.getFieldValue(this, fk);
                    if (fkValue != null) {
                        ar.read(fkValue);
                        Classes.setFieldValue(this, f.getName(), ar);
                    }
                }
            }
        }
    }

    void reloadAssociations() {
        associationProxies = new ArrayList<AssociationProxy>();
        readAssociations();
    }

    void readAggregations() {
        Class c = getClass();
        for (Field f : c.getDeclaredFields()) {
            for (Annotation a : f.getDeclaredAnnotations()) {
                if (a.annotationType().equals(ComposedOf.class)) {
                    // TODO implement
                }
            }
        }
    }

    void reloadAggregations() {
        readAggregations();
    }

    void readCallbacks() {
        Class c = getClass();
        for (Field f : c.getDeclaredFields()) {
            for (Annotation a : f.getDeclaredAnnotations()) {
                if (a.annotationType().equals(AfterCreate.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(AfterDestroy.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(AfterFind.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(AfterInitialization.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(BeforeCreate.class)) {
                    // TODO implement
                } else if (a.annotationType().equals(BeforeDestroy.class)) {
                    // TODO implement
                }
            }
        }
    }

    void reloadCallbacks() {
        readCallbacks();
    }

}
