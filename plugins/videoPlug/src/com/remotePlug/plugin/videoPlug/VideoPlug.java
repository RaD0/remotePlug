package com.remotePlug.plugin.videoPlug;

import com.remotePlug.components.Plugin;
import com.remotePlug.handlers.RequestHandler;

public class VideoPlug implements Plugin {
    @Override
    public boolean load() {
        System.out.println("Loaded VideoPlug");
        RequestHandler.getInstance().registerHandler(new VideoHandler());
        return true;
    }

    @Override
    public boolean unload() {
        return false;
    }
}
