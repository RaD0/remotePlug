package com.remotePlug.components;

import java.util.ServiceLoader;

public class PluginComponentLauncher implements ComponentLauncher {

    private final ServiceLoader<Plugin> plugins;

    PluginComponentLauncher() {
        plugins = ServiceLoader.load(Plugin.class);
    }

    public boolean loadPlugins() {
        for (Plugin plugin_ : plugins) {
            if (!plugin_.load()) {
                System.out.println("Failed to load plugin: " + plugin_.getClass());
                return false;
            }
        }
        return true;
    }

    public void unloadPlugins() {
        for (Plugin plugin : plugins) plugin.unload();
    }

    @Override
    public boolean load() {
        return loadPlugins();
    }

    @Override
    public String getLauncher() {
        return PluginComponentLauncher.class.getName();
    }

}
