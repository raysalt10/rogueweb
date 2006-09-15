package com.aetrion.activerecord.adapter;

import com.aetrion.activerecord.errors.UnsupportedTypeException;
import com.aetrion.activerecord.errors.ActiveRecordException;
import com.aetrion.activesupport.Strings;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * 
 */
public class Column {

    protected String name;
    protected Object defaultValue;
    protected ColumnType type;
    protected Integer limit;
    protected boolean nullAllowed = true;
    protected String sqlType;
    protected Integer precision;
    protected Integer scale;
    protected boolean primary = false;

    protected Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public Column(String name, Object defaultValue, String sqlType, boolean nullAllowed) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.sqlType = sqlType;
        this.limit = extractLimit(sqlType);
        this.precision = extractPrecision(sqlType);
        this.scale = extractScale(sqlType);
        this.type = simplifiedType(sqlType);
        this.nullAllowed = nullAllowed;
    }

    public String getName() {
        return name;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public ColumnType getType() {
        return type;
    }

    public Integer getLimit() {
        return limit;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    public String getSqlType() {
        return sqlType;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getScale() {
        return scale;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isText() {
        return type == ColumnType.TEXT || type == ColumnType.STRING;
    }

    public boolean isNumber() {
        return type == ColumnType.FLOAT || type == ColumnType.INTEGER || type == ColumnType.DECIMAL;
    }

    public Class klass() {
        switch (type) {
            case INTEGER:
                return Integer.class;
            case FLOAT:
                return Float.class;
            case DECIMAL:
                return BigDecimal.class;
            case DATETIME:
                return Date.class;
            case DATE:
                return Date.class;
            case TIMESTAMP:
                return Date.class;
            case TIME:
                return Date.class;
            case TEXT:
            case STRING:
                return String.class;
            case BINARY:
                return String.class;
            case BOOLEAN:
                return Boolean.class;
            default:
                throw new UnsupportedTypeException("Type not supported: " + type);
        }
    }

    public Object typeCast(String value) {
        switch (type) {
            case STRING:
                return value;
            case TEXT:
                return value;
            case INTEGER:
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    if (value == null) return 0;
                    return 1;
                }
            case FLOAT:
                return Float.parseFloat(value);
            case DECIMAL:
                return valueToDecimal(value);
            case DATETIME:
                return stringToTime(value);
            case TIMESTAMP:
                return stringToTime(value);
            case DATE:
                return stringToDummyTime(value);
            case BINARY:
                return binaryToString(value);
            case BOOLEAN:
                return valueToBoolean(value);
            default:
                return value;
        }
    }

    public String getHumanName() {
        return Strings.humanize(getName());
    }

    public static String stringToBinary(String value) {
        return value;
    }

    public static String binaryToString(String value) {
        return value;
    }

    public static Date stringToDate(String value) {
        if (value.equals("0000-00-00")) return null;
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        Date date;
        try {
            date = df.parse(value);
        } catch (ParseException e) {
            throw new ActiveRecordException(
                    "Error occurred parsing date: " + e.getMessage() + " Must be in format YYYY-MM-dd");
        }
        return date;
    }

    public static Date stringToTime(String value) {
        if (value.equals("0000-00-00 00:00:00")) return null;
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date date;
        try {
            date = df.parse(value);
        } catch (ParseException e) {
            throw new ActiveRecordException(
                    "Error occurred parsing date: " + e.getMessage() + " Must be in format YYYY-MM-dd HH:mm:ss");
        }
        return date;
    }

    public static Date stringToDummyTime(String value) {
        return stringToTime(value);
    }

    public static Boolean valueToBoolean(Object value) {
        if (value instanceof Boolean) return (Boolean) value;
        value = value.toString().toLowerCase();
        return value.equals("true") || value.equals("t") || value.equals("1");
    }

    public static BigDecimal valueToDecimal(Object value) {
        if (value instanceof BigDecimal) return (BigDecimal) value;
        return new BigDecimal(value.toString());
    }

    private Integer extractLimit(String sqlType) {
        Matcher m = Pattern.compile("\\((.*)\\)").matcher(sqlType.toLowerCase());
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return null;
        }
    }

    private Integer extractPrecision(String sqlType) {
        Matcher m = Pattern.compile("^(numeric|decimal|number)\\((\\d+)(,\\d+)?\\)").matcher(sqlType.toLowerCase());
        if (m.find()) {
            return Integer.parseInt(m.group(2));
        } else {
            return null;
        }
    }

    private Integer extractScale(String sqlType) {
        Matcher m = Pattern.compile("^(numeric|decimal|number)\\((\\d+)(,(\\d+))\\)").matcher(sqlType.toLowerCase());
        if (m.find()) {
            return Integer.parseInt(m.group(4));
        } else {
            m = Pattern.compile("^(numeric|decimal|number)\\((\\d+)(,\\d+)?\\)").matcher(sqlType.toLowerCase());
            if (m .find()) {
                return 0;
            }
        }
        return null;
    }

    /**
     * Convert a field type String to a ColumnType value. Throws an UnsupportedTypeException if the type cannot be
     * found.
     * @param fieldType The field type String
     * @return The ColumnType
     */
    protected ColumnType simplifiedType(String fieldType) {
        fieldType = fieldType.toLowerCase();
        if (fieldType.contains("int")) return ColumnType.INTEGER;
        if (fieldType.contains("float|double")) return ColumnType.FLOAT;
        if (fieldType.contains("decimal|numeric|number")) {
            if (extractScale(fieldType) == 0) {
                return ColumnType.INTEGER;
            } else {
                return ColumnType.DECIMAL;
            }
        }
        if (fieldType.contains("datetime")) return ColumnType.DATETIME;
        if (fieldType.contains("timestamp")) return ColumnType.TIMESTAMP;
        if (fieldType.contains("time")) return ColumnType.TIME;
        if (fieldType.contains("date")) return ColumnType.DATE;
        if (fieldType.contains("clob") || fieldType.contains("text")) return ColumnType.TEXT;
        if (fieldType.contains("blog") || fieldType.contains("binary")) return ColumnType.BINARY;
        if (fieldType.contains("char") || fieldType.contains("string")) return ColumnType.STRING;
        if (fieldType.contains("boolean")) return ColumnType.BOOLEAN;
        throw new UnsupportedTypeException("Unknown type " + fieldType);
    }
}
