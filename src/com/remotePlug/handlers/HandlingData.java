package com.remotePlug.handlers;

import java.util.Collection;
import java.util.Collections;

/**
 * An instance of this class will define the current data being handled.
 *
 * If a MediaPlayer is playing a video titled "MyAwesomeVideo.avi", then this Handling data would precisely show that.
 * These are to be attached with the handler
 */

public class HandlingData {

    private String startOperation;
    private Collection<String> permittedOperations;
    private boolean isActive;

    public HandlingData() {
        startOperation = "";
        permittedOperations = Collections.emptyList();
        isActive = false;
    }

    public void update(String newStartOperation, Collection<String> newPermittedOperations, boolean newActiveStatus) {
        if (null != newStartOperation) startOperation = newStartOperation;
        if (null != newPermittedOperations) permittedOperations = newPermittedOperations;
        isActive = newActiveStatus;
    }

    public String getStartOperation() {
        return startOperation;
    }

    public Collection<String> getPermittedOperations() {
        return permittedOperations;
    }

    public boolean isActive() {
        return isActive;
    }
}
