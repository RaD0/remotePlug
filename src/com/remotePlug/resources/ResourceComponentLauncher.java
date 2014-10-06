package com.remotePlug.resources;

import com.remotePlug.components.ComponentLauncher;
import com.remotePlug.handlers.RequestHandler;

public class ResourceComponentLauncher implements ComponentLauncher {

    @Override
    public boolean load() {
        ResourceManager.init();
        RequestHandler.getInstance().registerHandler(new DirectoryHandler());
        System.out.println("Launching "+ResourceComponentLauncher.class.getName());
        return true;
    }

    @Override
    public String getLauncher() {
        return ResourceComponentLauncher.class.getName();
    }
}
