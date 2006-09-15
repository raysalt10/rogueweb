package com.aetrion.activesupport;

/**
 * Benchmark class.
 *
 * @author Anthony Eden
 */
public class Benchmark {

    /**
     * Return the execution time of the specified block in milliseconds.
     * @param block The block
     * @return The number of milliseconds to execute
     */
    public static long realtime(BenchmarkBlock block) {
        long start = System.currentTimeMillis();
        block.execute();
        return System.currentTimeMillis() - start;
    }

    /**
     * Interface providing a callback method which is called by the benchmarker.
     */
    public interface BenchmarkBlock {

        /**
         * Execute the logic.
         */
        void execute();
    }

}
