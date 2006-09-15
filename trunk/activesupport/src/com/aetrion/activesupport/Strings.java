package com.aetrion.activesupport;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collection;
import java.util.Iterator;

/**
 * Utilities for working with Strings.
 */
public class Strings {

    private Strings() {
        // no op
    }

    /**
     * Tableize the given word.
     * @param word The word
     * @return The tabelized version of the word
     */
    public static String tableize(String word) {
//        String underscored = underscore(depackage(word));
//        System.out.println("Underscored and depacked of " + word + " is " + underscored);
//        String pluralized = pluralize(underscored);
//        System.out.println("Pluralized is " + pluralized);
//        return pluralized;
        return pluralize(underscore(depackage(word)));
    }

    /**
     * Pluralize the given word.
     * @param word The word
     * @return The pluralized version of the word
     */
    public static String pluralize(String word) {
        return Inflection.pluralize(word);
    }

    public static String singularize(String word) {
        return Inflection.singularize(word);
    }

    /**
     * Underscore the given word.
     * @param word The word
     * @return The underscored version of the word
     */
    public static String underscore(String word) {
        String firstPattern = "([A-Z]+)([A-Z][a-z])";
        String secondPattern = "([a-z\\d])([A-Z])";
        String replacementPattern = "$1_$2";
        word = word.replaceAll("\\.", "/"); // replace package separator with slash
        word = word.replaceAll("\\$", "__"); // replace $ with two underscores for inner classes
        word = word.replaceAll(firstPattern, replacementPattern); // replace capital letter with _ plus lowercase letter
        word = word.replaceAll(secondPattern, replacementPattern);
        word = word.replace('-', '_');
        word = word.toLowerCase();
        return word;
    }

    public static String camelize(String word) {
        return camelize(word, false);
    }

    public static String camelize(String word, boolean lowercaseFirstLetter) {
        // replace all slashes with dots (package separator)
        Pattern p = Pattern.compile("\\/(.?)");
        Matcher m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst("." + m.group(1)/*.toUpperCase()*/);
            m = p.matcher(word);
        }

        // uppercase the class name
        p = Pattern.compile("(\\.?)(\\w)([^\\.]*)$");
//        System.out.println("Does " + word + " match " + p + "?");
        m = p.matcher(word);
        if (m.find()) {
//            System.out.println("match! group count: " + m.groupCount());
//            for (int i = 1; i <= m.groupCount(); i++) {
//                System.out.println("group " + i + "=" + m.group(i));
//            }
            String rep = m.group(1) + m.group(2).toUpperCase() + m.group(3);
//            System.out.println("replacement string raw: " + rep);
            rep = rep.replaceAll("\\$", "\\\\\\$");
//            System.out.println("replacement string processed: " + rep);
            word = m.replaceAll(rep);
        }

        // replace two underscores with $ to support inner classes
        p = Pattern.compile("(__)(.)");
        m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst("\\$" + m.group(2).toUpperCase());
            m = p.matcher(word);
        }

        // remove all underscores
        p = Pattern.compile("(_)(.)");
        m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst(m.group(2).toUpperCase());
            m = p.matcher(word);
        }

        if (lowercaseFirstLetter) {
            word = word.substring(0, 1).toLowerCase() + word.substring(1);
        }

        return word;

    }

    public static String depackage(String word) {
        return word.replaceAll("^.*\\.", "");
    }

    public static String foreignKey(String className) {
        return foreignKey(className, true);
    }

    public static String foreignKey(String className, boolean separateIdWithUnderscore) {
        return underscore(depackage(className)) + (separateIdWithUnderscore ? "_id" : "id");
    }

    public static String titleize(String word) {
        word = humanize(underscore(word));
        Pattern p = Pattern.compile("\\b([a-z])");
        Matcher m = p.matcher(word);
        while (m.find()) {
            word = m.replaceFirst(capitalize(m.group(1)));
            m = p.matcher(word);
        }
        return word;
    }

    /**
     * Converts a table name into a class name.
     * @param tableName The table name
     * @return The class name
     */
    public static String classify(String tableName) {
        return camelize(singularize(tableName.replaceAll(".*\\.", "")));
    }

    public static String humanize(String word) {
        return capitalize(word.replaceAll("_id$", "").replaceAll("_", " "));
    }

    /**
     * Return the ordinal for the given number.
     * @param number The number
     * @return The ordinal String
     */
    public static String ordinalize(Number number) {
        return ordinalize(number.toString());
    }

    /**
     * Return the ordinal for the given number.
     * @param number The number
     * @return The ordinal String
     */
    public static String ordinalize(String number) {
        int[] _11through13 = {11, 12, 13};
        for (int n : _11through13) {
            int m100 = Integer.parseInt(number) % 100;
            if (Integer.toString(m100).endsWith(Integer.toString(n))) {
                return number + "th";
            }
        }

        int m = Integer.parseInt(number) % 10;
        switch (m) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }

    /**
     * Replace underscores with dashes.
     * @param word The word
     * @return The word with underscores converted to dashes
     */
    public static String dasherize(String word) {
        return word.replaceAll("_", "-");
    }

    // other utilities
    public static String capitalize(String word) {
        word = word.toLowerCase();
        word = word.substring(0, 1).toUpperCase() + word.substring(1);
        return word;
    }

    /**
     * Count the number of the specified characters in the String.
     * @param word The word
     * @param countThese The characters to count
     * @return The number of times the character appears
     */
    public static int count(String word, char countThese) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (c == countThese) {
                count++;
            }
        }
        return count;
    }

    /**
     * Join the specified collection with the given separator.
     * @param c The collection
     * @param separator The separator
     * @return The joined String
     */
    public static String join(Collection c, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator iter = c.iterator();
        while (iter.hasNext()) {
            Object o = iter.next();
            sb.append(o.toString());
            if (iter.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

}
