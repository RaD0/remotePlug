package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

public class PlugRequest {
    private final ResourceItem resourceItem;
    private final Object requestType;

    public PlugRequest(ResourceItem resourceItem, Object requestType) {
        this.resourceItem = resourceItem;
        this.requestType = requestType;
    }

    public ResourceItem getResourceItem() {
        return resourceItem;
    }

    public Object getRequestType() {
        return requestType;
    }
}
