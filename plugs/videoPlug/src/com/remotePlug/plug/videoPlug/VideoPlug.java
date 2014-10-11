package com.remotePlug.plug.videoPlug;

import com.remotePlug.components.Plug;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.settings.ApplicationSettings;
import org.apache.log4j.Logger;
import org.ini4j.Profile;

import java.util.*;

public class VideoPlug implements Plug {

    private Logger logger = Logger.getLogger(VideoPlug.class);

    @Override
    public boolean load() {
        RequestHandler.getInstance().registerHandler(new VideoHandler(getFormats()));
        return com.remotePlug.plug.videoPlug.mediaPlayer.MediaPlayerEngine.start();
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
            formats = new HashSet<>(Arrays.asList(formatsInCS));
        } catch (Exception e) {
            logger.error("Failed to get formats for plugin VideoPlug. Setting to default");
            formats = Collections.singleton("mp4");
        }
        return Collections.unmodifiableSet(formats);
    }
}
