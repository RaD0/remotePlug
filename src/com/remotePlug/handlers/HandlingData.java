package com.remotePlug.handlers;

import java.util.Collection;
import java.util.Collections;

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
