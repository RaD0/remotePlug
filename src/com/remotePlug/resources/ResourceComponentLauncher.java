package com.remotePlug.resources;

import com.remotePlug.components.ComponentLauncher;

public class ResourceComponentLauncher implements ComponentLauncher {

    @Override
    public boolean load() {
        ResourceManager.init();
        System.out.println("Launching "+ResourceComponentLauncher.class.getName());
        return true;
    }

    @Override
    public String getLauncher() {
        return ResourceComponentLauncher.class.getName();
    }
}
