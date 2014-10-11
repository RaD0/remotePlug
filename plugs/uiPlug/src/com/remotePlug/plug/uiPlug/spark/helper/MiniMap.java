package com.remotePlug.plug.uiPlug.spark.helper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Did not think about this much.
 * A simple custom map to map small stuff to to other list of small stuff
 */
public class MiniMap {
    String value;
    Set<String> keys = new HashSet<>();

    public MiniMap(String value, String... keys) {
        this.value = value;
        Collections.addAll(this.keys, keys);
    }

    public boolean match(String keyToMatch) {
        return (null != keyToMatch && keys.contains(keyToMatch));
    }

    public String getValue() {
        return value;
    }
}
