package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

/**
 * An instances of this defines the incoming request to the application
 * ResourceItem would be the item requested for and requestType would be telling us what to do with this item
 */

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
