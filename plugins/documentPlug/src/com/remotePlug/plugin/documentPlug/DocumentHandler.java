package com.remotePlug.plugin.documentPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceItem;

import java.util.Collection;
import java.util.Collections;

public class DocumentHandler implements Handler {

    final String[] offeredFormats = {"pdf", "txt", "doc"};

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getResourceItem()
                && FileUtilities.isAFile(request.getResourceItem())
                && isOffered(FileUtilities.toResourceFile(request.getResourceItem()).getFormat()));
    }

    @Override
    public void handle(PlugRequest request) {
        if(null != request && null != request.getResourceItem()) {
            System.out.printf("Document Handler handling: "+request.getResourceItem().getName());
        }
    }

    @Override
    public Collection<String> getPermittedOperations(ResourceItem item) {
        return Collections.singleton("N/A");
    }

    private boolean isOffered(String format) {
        for(String offeredFormat: offeredFormats)
            if(offeredFormat.equals(format))
                return true;
        return false;
    }
}
