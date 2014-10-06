package com.remotePlug.resources;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.HandlingData;
import com.remotePlug.handlers.PlugRequest;

import java.util.Collections;

public class DirectoryHandler implements Handler {

    DirectoryHandler() {}
    final String operation = "Open";

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getResourceItem()
                && FileUtilities.isADirectory(request.getResourceItem()));
    }

    @Override
    public void handle(PlugRequest request) {
        // Nothing to do here!
    }

    @Override
    public void packHandlingData(ResourceItem item, HandlingData handlingData) {
        handlingData.update(operation, Collections.singleton(operation), false);
    }

    @Override
    public ResourceItem getCurrentlyHandling() {
        // Todo!
        return null;
    }
}
