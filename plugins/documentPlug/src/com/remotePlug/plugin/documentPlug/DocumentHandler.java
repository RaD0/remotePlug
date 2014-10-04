package com.remotePlug.plugin.documentPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;

public class DocumentHandler implements Handler {

    final String[] offeredFormats = {"pdf", "txt", "doc"};

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getMediaItem()
                && isOffered(request.getMediaItem().getFormat()));
    }

    @Override
    public void handle(PlugRequest request) {
        if(null != request && null != request.getMediaItem()) {
            System.out.printf("Document Handler handling: "+request.getMediaItem().getName());
        }
    }

    private boolean isOffered(String format) {
        for(String offeredFormat: offeredFormats)
            if(offeredFormat.equals(format))
                return true;
        return false;
    }
}
