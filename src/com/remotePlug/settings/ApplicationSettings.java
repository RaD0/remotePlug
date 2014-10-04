package com.remotePlug.settings;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ApplicationSettings {

    private File resourceRoot;
    private Set<String> validReadableFormats;
    private static ApplicationSettings instance = null;

    ApplicationSettings() {
        if(null == instance)
            instance = this;
        validReadableFormats = new HashSet<String>();
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

    public boolean isValidFormat(String formatToCheck) {
        return (null != formatToCheck && validReadableFormats.contains(formatToCheck));
    }

    public File getResourceRoot() {
        return resourceRoot;
    }


}
