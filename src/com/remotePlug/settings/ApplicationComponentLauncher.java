package com.remotePlug.settings;

import com.remotePlug.components.ComponentLauncher;

import java.io.File;

/**
 * Loads up ApplicationSettings, by allowing ApplicationSettingsReader to read all settings and load them to ApplicationSettings
 */

public class ApplicationComponentLauncher implements ComponentLauncher {

    private final String settingsPath;

    public ApplicationComponentLauncher(String settingsPath) {
        this.settingsPath = settingsPath;
    }

    @Override
    public boolean load() {
        return (null != settingsPath
                && ApplicationSettingsReader.loadSettings(new File(settingsPath.concat("/settings.ini"))));
    }

    @Override
    public String getLauncher() {
        return ApplicationComponentLauncher.class.getName();
    }
}
