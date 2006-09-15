package com.aetrion.activesupport;

import java.text.NumberFormat;

/**
 * 
 */
public class Numbers {

    private Numbers() {
        // no op
    }

    public static String toSeconds(long milliseconds) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format((double) milliseconds / (double) 1000);
    }

}
