package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;

public interface Handler {
    public boolean canHandle(PlugRequest request);
    public void handle(PlugRequest request);
    public void packHandlingData(ResourceItem item, HandlingData handlingData);
    public ResourceItem getCurrentlyHandling();
}
