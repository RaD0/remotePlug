package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceMediaItem;

public class PlugRequest {
    private final ResourceMediaItem mediaItem;
    private final Object requestType;

    public PlugRequest(ResourceMediaItem mediaItem, Object requestType) {
        this.mediaItem = mediaItem;
        this.requestType = requestType;
    }

    public ResourceMediaItem getMediaItem() {
        return mediaItem;
    }

    public Object getRequestType() {
        return requestType;
    }
}
