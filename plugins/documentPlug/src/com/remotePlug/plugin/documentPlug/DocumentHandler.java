package com.remotePlug.plugin.documentPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.resources.ResourceMediaItem;

public class DocumentHandler implements Handler {

    final String[] offeredFormats = {"pdf", "txt", "doc"};

    @Override
    public boolean canHandle(ResourceMediaItem media) {
        return (null != media && isOffered(media.getFormat()));
    }

    @Override
    public void handle(ResourceMediaItem media) {
        if(null != media) {
            System.out.printf("Document Handler handling: "+media.getName());
        }
    }

    private boolean isOffered(String format) {
        for(String offeredFormat: offeredFormats)
            if(offeredFormat.equals(format))
                return true;
        return false;
    }
}
