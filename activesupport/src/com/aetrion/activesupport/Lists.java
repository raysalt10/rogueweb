package com.aetrion.activesupport;

import java.util.List;
import java.util.ArrayList;

/**
 * Utilities for working with lists.
 *
 * @author Anthony Eden
 */
public class Lists {

    private Lists() {
        // no op
    }

    /**
     * Compact the list, removing null items.
     * @param list The list
     * @return The compacted list
     */
    public static List compact(List list) {
        List newList = new ArrayList();
        for (Object o : list) {
            if (o != null) newList.add(o);
        }
        return newList;
    }

}
