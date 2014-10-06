package com.remotePlug.plugin.documentPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.resources.FileUtilities;
import com.remotePlug.resources.ResourceItem;

import java.util.*;

public class DocumentHandler implements Handler {

    Set<String> offeredFormats;

    DocumentHandler(Set<String> formats) {
        this.offeredFormats = formats;
    }

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getResourceItem()
                && FileUtilities.isAFile(request.getResourceItem())
                && offeredFormats.contains(FileUtilities.toResourceFile(request.getResourceItem()).getFormat()));
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

}
