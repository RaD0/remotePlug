package com.remotePlug.resources;

import com.remotePlug.components.ComponentLauncher;
import com.remotePlug.handlers.RequestHandler;

/**
 * Boots up the ResourceManager and adds a handler to handle requests for opening a directory
 */

public class ResourceComponentLauncher implements ComponentLauncher {

    @Override
    public boolean load() {
        ResourceManager.init();
        RequestHandler.getInstance().registerHandler(new DirectoryHandler());
        return true;
    }

    @Override
    public String getLauncher() {
        return ResourceComponentLauncher.class.getName();
    }
}
