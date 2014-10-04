package com.remotePlug.plugin.videoPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.handlers.RequestHandler;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;

public class VideoPlug implements Plugin {
    @Override
    public boolean load() {
        System.out.println("Loading VideoPlug");
        RequestHandler.getInstance().registerHandler(new VideoHandler());
        return MediaPlayerEngine.start();
    }

    @Override
    public boolean unload() {
        return false;
    }
}
