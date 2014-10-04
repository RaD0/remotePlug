package com.remotePlug.components;

import com.remotePlug.resources.ResourceComponentLauncher;
import com.remotePlug.settings.ApplicationComponentLauncher;

import java.util.LinkedList;
import java.util.Queue;

public class CoreComponentsLauncher {

    private Queue<ComponentLauncher> launchers;
    private static CoreComponentsLauncher instance = null;

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
        launchers = new LinkedList<ComponentLauncher>();
        launchers.add(new ApplicationComponentLauncher(settingsPath));
        launchers.add(new ResourceComponentLauncher());
        launchers.add(new PluginComponentLauncher());
    }

    private boolean launch() {
        while (!launchers.isEmpty()) {
            ComponentLauncher launcher = launchers.poll();
            if (!launcher.load()) {
                System.out.println("Failed to load launcher: "+launcher.getLauncher());
                return false;
            }
        }
        return true;
    }

}
