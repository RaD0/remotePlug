package com.remotePlug.components;

import org.apache.log4j.Logger;

import java.util.ServiceLoader;

/**
 * Loads all the plugs defined in the application.
 * To define a Plug, the main class of the Plug should implement the Interface Plug.
 *
 * Making use of Java's service loader, the PluginComponentLauncher loads and launches all the plugs
 */
public class PluginComponentLauncher implements ComponentLauncher {

    private final ServiceLoader<Plug> plugs;
    private static Logger logger = Logger.getLogger(PluginComponentLauncher.class);

    PluginComponentLauncher() {
        plugs = ServiceLoader.load(Plug.class);
    }

    /**
     * Loads and launches all the plugs
     * @return True if all the Plugs.load() return true
     */
    public boolean loadPlugins() {
        for (Plug plug_ : plugs) {
            if (!plug_.load()) {
                logger.error("Failed to load plugin: " + plug_.getClass());
                return false;
            }
        }
        return true;
    }

    public void unloadPlugins() {
        for (Plug plug : plugs) plug.unload();
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
