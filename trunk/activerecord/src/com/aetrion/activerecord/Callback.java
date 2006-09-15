package com.aetrion.activerecord;

/**
 * Standard callback interface.
 *
 * @author Anthony Eden
 */
public interface Callback {

    /**
     * Implement this method to receive a callback.
     * @param args The args
     * @return The result or null
     */
    public Object execute(Object... args);

}
