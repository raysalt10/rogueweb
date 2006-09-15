package com.aetrion.activesupport;

/**
 * 
 */
public abstract class Options {

    /**
     * Construct options from the Object array.
     * @param options The object array
     */
    public Options(Object... options) {
        for (int i = 0; i < options.length; i += 2) {
            String optionName = String.valueOf(options[i]);
            Classes.setFieldValue(this, optionName, options[i + 1]);
        }
    }

}
