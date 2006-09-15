package com.aetrion.activerecord.adapter;

/**
 * 
 */
public class NativeType {

    private String name;
    private Integer limit;
    private Integer precision;
    private Integer scale;

    public NativeType(String name) {
        this(name, null, null, null);
    }

    public NativeType(String name, Integer limit) {
        this(name, limit, null, null);
    }

    /**
     * Construct a new NativeType instance.
     * @param name The name of the native type
     * @param limit The limit
     * @param precision The precision
     * @param scale The scale
     */
    public NativeType(String name, Integer limit, Integer precision, Integer scale) {
        this.name = name;
        this.limit = limit;
        this.precision = precision;
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPrecision() {
        return precision;
    }

    public Integer getScale() {
        return scale;
    }

}
