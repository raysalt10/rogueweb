package com.aetrion.activesupport;

import java.io.File;
import java.io.FileReader;

/**
 * Common IO utilities.
 *
 * @author Anthony Eden
 */
public class IO {

    /**
     * Read all of the data from the specified file into a String.
     * @param file The File
     * @return A String of the data
     * @throws IOException
     */
    public static String read(File file) {
        try {
            StringBuilder sb = new StringBuilder();
            FileReader reader = new FileReader(file);
            int c = -1;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString();
        } catch (java.io.IOException e) {
            throw new IOException("Error reading data from " + file);
        }
    }
}
