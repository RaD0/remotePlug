package com.remotePlug.components;

/**
 * The ComponentLauncher defines a blue print to create a launcher for a certain Component in the application.
 *
 * This is a core feature. To register and load a ComponentLauncher, it should be added to CoreComponentsLauncher which
 * loads and launches all the core components
 *
 * External plugs are not allowed to launch a ComponentLauncher, unless it's mentioned in the CoreComponentsLauncher
 */

public interface ComponentLauncher {
    public boolean load();
    public String getLauncher();
}
