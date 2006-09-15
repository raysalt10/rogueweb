package com.aetrion.activesupport;

import junit.framework.TestCase;

import java.util.List;

/**
 * 
 */
public class ClassesTest extends TestCase {

    private String pathFilter = "com/aetrion";

    public void testListClasses() {
        String className = getClass().getName();
        List<String> classNames = Classes.list(pathFilter);
        assertTrue("Class " + className + " expected but not found", classNames.contains(className));
    }

    public void testFindClass() {
        String pattern = "ClassesTes";
        Class c = Classes.findClass(pattern, pathFilter);
        assertNotNull("Could not find a class matching pattern " + pattern, c);
    }

    public void testFindField() {
        Person p = new Person();
        p.name = "Anthony";
        assertEquals(p.name, Classes.getFieldValue(p, "name"));
    }

    private void debug(List<String> classNames) {
         for (String className : classNames) {
            System.out.println(className + " == " + getClass().getName() + "?");
            if (className.equals(getClass().getName())) {
                System.out.println("match!");
            }

        }
    }

    class Person {
        private String name;
    }

}
