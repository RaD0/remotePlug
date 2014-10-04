package com.remotePlug.settings;

import org.ini4j.Profile;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class ApplicationSettingsReader {

    static boolean loadSettings(File settingsIniFile) {
        if(null == settingsIniFile || !settingsIniFile.exists())
            return false;
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
            return false;
        }
        return true;
    }

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
