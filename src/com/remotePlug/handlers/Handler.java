package com.remotePlug.handlers;

import com.remotePlug.resources.ResourceItem;
import java.util.Collection;

public interface Handler {
    public boolean canHandle(PlugRequest request);
    public void handle(PlugRequest request);
    public Collection<String> getPermittedOperations(ResourceItem item);
}
