package com.remotePlug.plugin.documentPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.settings.ApplicationSettings;
import org.ini4j.Profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DocumentPlug implements Plugin {

    @Override
    public boolean load() {
        RequestHandler.getInstance().registerHandler(new DocumentHandler(getFormats()));
        return true;
    }

    @Override
    public boolean unload() {
        return false;
    }

    private Set<String> getFormats() {
        Profile.Section videoSettings = ApplicationSettings.getInstance().getSettings("Plugin-DocumentPlug");
        Set<String> formats;
        try {
            String[] formatsInCS = videoSettings.get("formats").split(",");
            formats = new HashSet<>(Arrays.asList(formatsInCS));
        } catch (Exception e) {
            formats = new HashSet<>(Arrays.asList("txt", "doc"));
        }
        return Collections.unmodifiableSet(formats);
    }
}
