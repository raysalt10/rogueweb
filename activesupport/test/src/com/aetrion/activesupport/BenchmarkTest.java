package com.aetrion.activesupport;

import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 */
public class BenchmarkTest extends TestCase {
    public void testRealtime() {
        final Map<String,String> testMap = new HashMap<String,String>();
        long value = Benchmark.realtime(new Benchmark.BenchmarkBlock() {
            public void execute() {
                for (int i = 0; i < 10000; i++) {
                    testMap.put("foo" + i, "bar");
                }
            }
        });
        System.out.println("Benchmark result: " + value + "ms (" + Numbers.toSeconds(value) + "s)");
        assertTrue("Value is not greater than 0", value > 0);
        assertTrue("Test map did not contain 'foo1' as expected", testMap.containsKey("foo1"));
        assertEquals("bar", testMap.get("foo1"));
    }
}
