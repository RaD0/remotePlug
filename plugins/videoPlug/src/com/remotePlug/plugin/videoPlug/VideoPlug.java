package com.remotePlug.plugin.videoPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;
import com.remotePlug.settings.ApplicationSettings;
import org.ini4j.Profile;

import java.util.*;

public class VideoPlug implements Plugin {

    @Override
    public boolean load() {
        RequestHandler.getInstance().registerHandler(new VideoHandler(getFormats()));
        return MediaPlayerEngine.start();
    }

    @Override
    public boolean unload() {
        return false;
    }

    private Set<String> getFormats() {
        Profile.Section videoSettings = ApplicationSettings.getInstance().getSettings("Plugin-VideoPlug");
        Set<String> formats;
        try {
            String[] formatsInCS = videoSettings.get("formats").split(",");
            formats = new HashSet<String>(Arrays.asList(formatsInCS));
        } catch (Exception e) {
            formats = Collections.singleton("mp4");
        }
        return Collections.unmodifiableSet(formats);
    }
}
