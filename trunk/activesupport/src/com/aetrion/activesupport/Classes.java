package com.aetrion.activesupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Utilities for working with classes.
 * @author Anthony Eden
 */
public class Classes {

    /** Internal cache for looking up class names. */
    private static ClassNameCache classNameCache = new ClassNameCache();

    /**
     * Get all class names in the classpath.
     * @return A List of class names
     */
    public static List<String> list() {
        return list(null);
    }

    /**
     * Get all class names in the classpath.
     * @param filter A path filter or null
     * @return A List of class names
     */
    public static List<String> list(String filter) {
        List<String> classes = new ArrayList<String>();
        String classPath = System.getProperty("java.class.path");
        // System.out.println("Class path: " + classPath);
        for (String classSource : classPath.split(":")) {
            // System.out.println("Looking in: " + classSource);
            if (classSource.endsWith(".jar") || classSource.endsWith(".zip")) {
                classes.addAll(findClassesInArchive(new File(classSource), filter));
            } else {
                classes.addAll(findClassesInDirectory(classSource, new File(classSource), filter));
            }
        }
        return classes;
    }

    /**
     * Find all of the class names in the specified archive file.
     * @param f The archive file
     * @return A List of class names
     */
    public static List<String> findClassesInArchive(File f) {
        return findClassesInArchive(f, null);
    }

    /**
     * Find all of the class names in the specified archive file.
     * @param f The archive file
     * @param filter An optional file path filter
     * @return A List of class names
     */
    public static List<String> findClassesInArchive(File f, String filter) {
        List<String> classes = new ArrayList<String>();
        try {
            ZipFile file = new ZipFile(f);
            Enumeration<? extends ZipEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName();
                    if (filter != null && !className.contains(filter)) continue;
                    className = className.substring(0, className.length() - ".class".length());
                    if (className.startsWith("/")) className = className.substring(1);
                    className = className.replaceAll("/", ".");
                    classes.add(className);
                    classNameCache.put(className, System.currentTimeMillis());
                }
            }
        } catch (java.io.IOException e) {
            throw new IOException("Error loading zip file: " + f);
        }
        return classes;
    }

    /**
     * Find all of the classes in the given directory. The base path is used to remove everything from the path except
     * the actual fully-qualified class path.
     * @param basePath The base path
     * @param d The directory to search
     * @return A List of class names
     */
    public static List<String> findClassesInDirectory(String basePath, File d) {
        return findClassesInDirectory(basePath, d, null);
    }

    /**
     * Find all of the classes in the given directory. The base path is used to remove everything from the path except
     * the actual fully-qualified class path.
     * @param basePath The base path
     * @param d The directory to search
     * @param filter An optional file name filter
     * @return A List of class names
     */
    public static List<String> findClassesInDirectory(String basePath, File d, String filter) {
        List<String> classes = new ArrayList<String>();
        for (File f : d.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(findClassesInDirectory(basePath, f, filter));
            } else {
                if (f.getName().endsWith(".class")) {
                    String path = f.getPath();
                    String className = path.substring(basePath.length());
                    if (filter != null && !className.contains(filter)) continue;
                    className = className.substring(0, className.length() - ".class".length());
                    if (className.startsWith("/")) className = className.substring(1);
                    className = className.replaceAll("/", ".");
//                    System.out.println(className);
                    classes.add(className);
                    classNameCache.put(className, System.currentTimeMillis());
                }
            }
        }
        return classes;
    }

    /**
     * Find the class which matches the given pattern.
     * @param pattern The regex pattern
     * @return The Class or null
     */
    public static Class findClass(String pattern) {
        return findClass(pattern, null);
    }

    /**
     * Find classes which match the given pattern.
     * @param pattern The regex pattern
     * @param filter An optional path filter
     * @return The Class or null
     */
    public static Class findClass(String pattern, String filter) {
        String className = classNameCache.find(pattern);
        if (className == null) {
            list(filter); // looks up and caches all classes
            className = classNameCache.find(pattern);
        }
        if (className != null) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new ClassException("Unable to load class " + className);
            }
        } else {
            return null;
        }
    }

    public static Object newInstance(Type t) {
        if (t instanceof Class) {
            return newInstance((Class) t);
        } else {
            throw new IllegalArgumentException("Only class type is currently supported");
        }
    }

    public static Object newInstance(Class c) {
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            throw new ClassException("Cannot instantiate " + c);
        } catch (IllegalAccessException e) {
            throw new ClassException("Empty constructor in " + c + " is inaccessible");
        }
    }

    public static Object getFieldValue(Object o, String name) {
        String fieldName = Strings.camelize(name, true);
        Class c = o.getClass();
        while (c != null) {
//            System.out.println("looking for field " + fieldName + " in " + c);
            Field f = null;
            try {
                f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(o);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new ClassException("Field " + name + " in " + c + " is inaccessible");
            } finally {
                if (f != null) f.setAccessible(false);
            }
        }
        return null;
    }

    public static void setFieldValue(Object o, String name, Object value) {
        String fieldName = Strings.camelize(name, true);
        Class c = o.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
//                System.out.println("Setting " + fieldName + " to " + value + " of type " + value.getClass() + " on " + c);
                f.set(o, value);
                f.setAccessible(false);
                return;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new ClassException("Field " + name + " in " + c + " is inaccessible");
            }
        }
    }

    /**
     * Internal class name cache. The key is the class name, the value is the time when it was inserted in the cache.
     * @author Anthony Eden
     */
    static class ClassNameCache extends HashMap<String, Long> {
        public String find(String pattern) {
            Pattern p = Pattern.compile(pattern);
            for (String key : keySet()) {
                if (p.matcher(key).find()) return key;
            }
            return null;
        }
    }

}
