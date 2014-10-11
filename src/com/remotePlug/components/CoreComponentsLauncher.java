package com.remotePlug.components;

import com.remotePlug.resources.ResourceComponentLauncher;
import com.remotePlug.settings.ApplicationComponentLauncher;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class loads all the components defined in this application.
 * All the components which are to be loaded should be of type ComponentLauncher
 * To add a component, add it to the <code>launchers</code> queue in <code>loadLaunchers(String settingsPath)</code>
 */
public class CoreComponentsLauncher {

    private Queue<ComponentLauncher> launchers;
    private static CoreComponentsLauncher instance = null;
    private Logger logger = Logger.getLogger(CoreComponentsLauncher.class);

    private CoreComponentsLauncher() {}

    public static CoreComponentsLauncher getInstance() {
        if(null == instance)
            instance = new CoreComponentsLauncher();
        return instance;
    }

    public boolean launch(String settingsPath) {
        loadLaunchers(settingsPath);
        return launch();
    }

    private void loadLaunchers(String settingsPath) {
        launchers = new LinkedList<>();
        launchers.add(new ApplicationComponentLauncher(settingsPath));
        launchers.add(new ResourceComponentLauncher());
        launchers.add(new PluginComponentLauncher());
    }

    /**
     * Loop through the launchers queue and load each ComponentLauncher
     * @return True if all the ComponentLaunchers.load() in launchers return true
     */
    private boolean launch() {
        while (!launchers.isEmpty()) {
            ComponentLauncher launcher = launchers.poll();
            if (!launcher.load()) {
                logger.error("Failed to load launcher: "+launcher.getLauncher());
                return false;
            }
        }
        return true;
    }

}
