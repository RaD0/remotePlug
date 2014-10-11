package com.remotePlug.settings;

import org.apache.log4j.Logger;
import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class ApplicationSettingsReader {

    private static Logger logger = Logger.getLogger(ApplicationSettingsReader.class);

    /**
     * Reads settings from settings.ini file and loads them to ApplicationSettings
     * @param settingsIniFile, this file should contain the settings of the application.
     * @return True is the passed settings file is valid and the settings has all the mandatory settings initiated
     */
    static boolean loadSettings(File settingsIniFile) {
        if(null == settingsIniFile || !settingsIniFile.exists()) {
            logger.error("Invalid settings file passed");
            return false;
        }

        try {
            Wini winiReader = new Wini(settingsIniFile);
            Profile.Section resources = winiReader.get("resources");
            if(null == resources) return false;
            File root = getResourcePath(resources.get("path"));
            if(null == root) return false;
            String[] formats = getFormats(resources.get("validFormats"));
            if(null == formats || formats.length < 1) return false;

            ApplicationSettings applicationSettings = new ApplicationSettings();
            applicationSettings.addResourceRoot(root);
            applicationSettings.addValidFormats(formats);

            winiReader.remove(resources);
            for (String key : winiReader.keySet()) {
                if (null != key && !key.isEmpty())
                    applicationSettings.addSetting(key, winiReader.get(key));
            }

        } catch (IOException e) {
            logger.error("Failed to read settings from settings.ini", e);
            return false;
        }
        return true;
    }

    // Helper functions

    private static File getResourcePath(String resourcePath) {
        if(!passBasicCheck(resourcePath)) return null;
        File resourceRoot = new File(resourcePath);
        if(resourceRoot.exists() && resourceRoot.isDirectory())
            return resourceRoot;
        else return null;
    }

    private static String[] getFormats(String formatsInCSV) {
        return passBasicCheck(formatsInCSV) ? formatsInCSV.split(",") : null;
    }

    private static boolean passBasicCheck(String valueToCheck) {
        return (null != valueToCheck && !valueToCheck.isEmpty());
    }

}
