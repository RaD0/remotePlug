package com.remotePlug.settings;

import org.ini4j.Profile;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ApplicationSettings {

    private File resourceRoot;
    private Set<String> validReadableFormats;
    private static ApplicationSettings instance = null;
    private HashMap<String,Profile.Section> otherSettings;

    ApplicationSettings() {
        if(null == instance)
            instance = this;
        validReadableFormats = new HashSet<String>();
        otherSettings = new HashMap<String, Profile.Section>();
    }

    public static ApplicationSettings getInstance() {
        return instance;
    }

    void addResourceRoot(File resourceRootPath) {
        this.resourceRoot = resourceRootPath;
    }

    void addValidFormats(String[] CSVSeparatedFormats) {
        Collections.addAll(validReadableFormats, CSVSeparatedFormats);
    }

    void addSetting(String key, Profile.Section value) {
        if (null != key)
            otherSettings.put(key, value);
    }

    public boolean isValidFormat(String formatToCheck) {
        return (null != formatToCheck && validReadableFormats.contains(formatToCheck));
    }

    public File getResourceRoot() {
        return resourceRoot;
    }

    public String getSetting(String sectionName, String sectionKey) {
        Profile.Section values = otherSettings.get(sectionName);
        return (null == values) ? null : values.get(sectionKey);
    }

    public Profile.Section getSettings(String sectionName) {
        return (null == sectionName) ? null : otherSettings.get(sectionName);
    }
}
