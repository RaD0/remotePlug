package com.remotePlug.plugin.videoPlug;

import com.remotePlug.handlers.Handler;
import com.remotePlug.handlers.PlugRequest;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayer;
import com.remotePlug.plugin.videoPlug.mediaPlayer.MediaPlayerEngine;
import com.remotePlug.resources.ResourceMediaItem;


public class VideoHandler implements Handler {

    final String acceptableFormat = "mp4";

    @Override
    public boolean canHandle(PlugRequest request) {
        return (null != request
                && null != request.getMediaItem()
                && acceptableFormat.equals(request.getMediaItem().getFormat()));
    }

    @Override
    public void handle(PlugRequest request) {
        if(null != request && null != request.getMediaItem()) {
            ResourceMediaItem media = request.getMediaItem();
            MediaPlayer.Option requestType = convertToOption((String) request.getRequestType());
            if(null == requestType) return;
            MediaPlayerEngine.getInstance().operatePlayer(media, requestType);
            System.out.println("VideoPlug Handler handling: "+media.getName());
        }
    }

    private MediaPlayer.Option convertToOption(String option) {
        if (null == option) return null;
        for(MediaPlayer.Option option_ : MediaPlayer.Option.values()) {
            if (option_.toString().equals(option))
                return option_;
        }
        return null;
    }
}
