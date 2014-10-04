package com.remotePlug.plugin.videoPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.resources.ResourceMediaItem;
import org.omg.SendingContext.RunTime;


public class VideoHandler implements Handler {

    final String acceptableFormat = "mp4";

    @Override
    public boolean canHandle(ResourceMediaItem media) {
        return (null != media && acceptableFormat.equals(media.getFormat()));
    }

    @Override
    public void handle(ResourceMediaItem media) {
        if(null != media) {
            System.out.println("VideoPlug Handler handling: "+media.getName());
            try {
                Runtime.getRuntime().exec("vlc "+media.getFile().getPath());
            } catch (Exception e) {
                System.out.println("Failed to play: "+media.getName());
            }

        }
    }
}
