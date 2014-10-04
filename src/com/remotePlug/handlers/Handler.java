package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceMediaItem;

public interface Handler {
    public boolean canHandle(ResourceMediaItem media);
    public void handle(ResourceMediaItem media);
}
